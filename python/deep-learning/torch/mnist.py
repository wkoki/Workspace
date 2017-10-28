# python2 と python3の仕様を揃える
from __future__ import print_function
# ライブラリを読み込み
import argparse
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms
from torch.autograd import Variable

# 引数を処理
parser = argparse.ArgumentParser(description='PyTorch MNIST Example')
parser.add_argument('--batch-size', type=int, default=64, metavar='N',
                    help='input batch size for training (default: 64)')
parser.add_argument('--test-batch-size', type=int, default=1000, metavar='N',
                    help='input batch size for testing (default: 1000)')
parser.add_argument('--epochs', type=int, default=10, metavar='N',
                    help='number of epochs to train (default: 10)')
parser.add_argument('--lr', type=float, default=0.01, metavar='LR',
                    help='learning rate (default: 0.01)')
parser.add_argument('--momentum', type=float, default=0.5, metavar='M',
                    help='SGD momentum (default: 0.5)')
parser.add_argument('--no-cuda', action='store_true', default=False,
                    help='disables CUDA training')
parser.add_argument('--seed', type=int, default=1, metavar='S',
                    help='random seed (default: 1)')
parser.add_argument('--log-interval', type=int, default=100, metavar='N',
                    help='how many batches to wait before logging training status')
args = parser.parse_args()
# GPUを使うか？
args.cuda = not args.no_cuda and torch.cuda.is_available()

# 乱数の初期化
torch.manual_seed(args.seed)
if args.cuda:
    torch.cuda.manual_seed(args.seed)


# データを自動的にロードするインスタンスを作成
kwargs = {'num_workers': 1, 'pin_memory': True} if args.cuda else {}
train_loader = torch.utils.data.DataLoader(
    datasets.MNIST('../data', train=True, download=True,
                   transform=transforms.Compose([
                       transforms.ToTensor(),
                       transforms.Normalize((0.1307,), (0.3081,))
                   ])),
    batch_size=args.batch_size, shuffle=True, **kwargs)
test_loader = torch.utils.data.DataLoader(
    datasets.MNIST('../data', train=False, transform=transforms.Compose([
                       transforms.ToTensor(),
                       transforms.Normalize((0.1307,), (0.3081,))
                   ])),
    batch_size=args.test_batch_size, shuffle=True, **kwargs)


# ニューラルネットワークをインスタンス化，GPUに送る
model = nn.Sequential(
    # 畳み込む
    nn.Conv2d(1, 10, kernel_size=5),  # 28 x 28 -> 24 x 24
    nn.MaxPool2d(2),                  # 24 x 24 -> 12 x 12
    nn.ReLU(True),
    # 畳み込む
    nn.Conv2d(10, 20, kernel_size=5), # 12 x 12 ->  8 x  8
    nn.MaxPool2d(2),                  #  8 x  8 ->  4 x  4
    nn.ReLU(True),
    # 全結合の代わりに畳み込む
    nn.Conv2d(20, 50, kernel_size=4), #  4 x  4 ->  1 x  1
    nn.ReLU(True),
    # 全結合の代わりに畳み込む
    nn.Conv2d(50, 10, kernel_size=1),
    nn.LogSoftmax(),
)
if args.cuda:
    model.cuda()


# 最適化アルゴリズムをインスタンス化
optimizer = optim.SGD(model.parameters(), lr=args.lr, momentum=args.momentum)

# 学習する関数を定義
def train(epoch):
    # ニューラルネットワークを学習モードにする
    model.train()
    # データを順番にロードする
    for batch_idx, (data, target) in enumerate(train_loader):
        # GPUを使う場合，データをGPUに送る
        if args.cuda:
            data, target = data.cuda(), target.cuda()
        # 計算グラフ用のラッパーに入れる
        data, target = Variable(data), Variable(target)
        # 勾配をゼロで初期化
        optimizer.zero_grad()
        # データをニューラルネットワークに入れて出力を求める
        output = model(data).view(-1,10)
        # 誤差関数の値を計算
        loss = F.nll_loss(output, target)
        # 誤差関数の値を最小化する勾配を計算
        loss.backward()
        # 最適化アルゴリズムによる勾配降下法を実施
        optimizer.step()
        # 途中結果を出力
        if batch_idx % args.log_interval == 0:
            print('Train Epoch: {} [{}/{} ({:.0f}%)]\tLoss: {:.6f}'.format(
                epoch, batch_idx * len(data), len(train_loader.dataset),
                100. * batch_idx / len(train_loader), loss.data[0]))


# 検証データ用の関数を定義
def test():
    # ニューラルネットワークを検証モードにする
    model.eval()
    # 平均的な誤差関数の値と正解率を求める
    test_loss = 0
    correct = 0
    # データを順番にロードする
    for data, target in test_loader:
        if args.cuda:
            data, target = data.cuda(), target.cuda()
        data, target = Variable(data, volatile=True), Variable(target)
        output = model(data).view(-1,10)
        test_loss += F.nll_loss(output, target, size_average=False).data[0] # sum up batch loss
        # 予測ラベルを求める
        pred = output.data.max(1, keepdim=True)[1] # get the index of the max log-probability
        # 正解率を求める
        correct += pred.eq(target.data.view_as(pred)).cpu().sum()

    # 結果を出力
    test_loss /= len(test_loader.dataset)
    print('\nTest set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)\n'.format(
        test_loss, correct, len(test_loader.dataset),
        100. * correct / len(test_loader.dataset)))


# args.epochsだけループする
for epoch in range(1, args.epochs + 1):
    # 訓練する
    train(epoch)
    # 検証する
    test()


