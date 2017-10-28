# python2 �� python3�̎d�l�𑵂���
from __future__ import print_function
# ���C�u������ǂݍ���
import argparse
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
from torchvision import datasets, transforms
from torch.autograd import Variable

# ����������
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
# GPU���g�����H
args.cuda = not args.no_cuda and torch.cuda.is_available()

# �����̏�����
torch.manual_seed(args.seed)
if args.cuda:
    torch.cuda.manual_seed(args.seed)


# �f�[�^�������I�Ƀ��[�h����C���X�^���X���쐬
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


# �j���[�����l�b�g���[�N���C���X�^���X���CGPU�ɑ���
model = nn.Sequential(
    # ��ݍ���
    nn.Conv2d(1, 10, kernel_size=5),  # 28 x 28 -> 24 x 24
    nn.MaxPool2d(2),                  # 24 x 24 -> 12 x 12
    nn.ReLU(True),
    # ��ݍ���
    nn.Conv2d(10, 20, kernel_size=5), # 12 x 12 ->  8 x  8
    nn.MaxPool2d(2),                  #  8 x  8 ->  4 x  4
    nn.ReLU(True),
    # �S�����̑���ɏ�ݍ���
    nn.Conv2d(20, 50, kernel_size=4), #  4 x  4 ->  1 x  1
    nn.ReLU(True),
    # �S�����̑���ɏ�ݍ���
    nn.Conv2d(50, 10, kernel_size=1),
    nn.LogSoftmax(),
)
if args.cuda:
    model.cuda()


# �œK���A���S���Y�����C���X�^���X��
optimizer = optim.SGD(model.parameters(), lr=args.lr, momentum=args.momentum)

# �w�K����֐����`
def train(epoch):
    # �j���[�����l�b�g���[�N���w�K���[�h�ɂ���
    model.train()
    # �f�[�^�����ԂɃ��[�h����
    for batch_idx, (data, target) in enumerate(train_loader):
        # GPU���g���ꍇ�C�f�[�^��GPU�ɑ���
        if args.cuda:
            data, target = data.cuda(), target.cuda()
        # �v�Z�O���t�p�̃��b�p�[�ɓ����
        data, target = Variable(data), Variable(target)
        # ���z���[���ŏ�����
        optimizer.zero_grad()
        # �f�[�^���j���[�����l�b�g���[�N�ɓ���ďo�͂����߂�
        output = model(data).view(-1,10)
        # �덷�֐��̒l���v�Z
        loss = F.nll_loss(output, target)
        # �덷�֐��̒l���ŏ���������z���v�Z
        loss.backward()
        # �œK���A���S���Y���ɂ����z�~���@�����{
        optimizer.step()
        # �r�����ʂ��o��
        if batch_idx % args.log_interval == 0:
            print('Train Epoch: {} [{}/{} ({:.0f}%)]\tLoss: {:.6f}'.format(
                epoch, batch_idx * len(data), len(train_loader.dataset),
                100. * batch_idx / len(train_loader), loss.data[0]))


# ���؃f�[�^�p�̊֐����`
def test():
    # �j���[�����l�b�g���[�N�����؃��[�h�ɂ���
    model.eval()
    # ���ϓI�Ȍ덷�֐��̒l�Ɛ��𗦂����߂�
    test_loss = 0
    correct = 0
    # �f�[�^�����ԂɃ��[�h����
    for data, target in test_loader:
        if args.cuda:
            data, target = data.cuda(), target.cuda()
        data, target = Variable(data, volatile=True), Variable(target)
        output = model(data).view(-1,10)
        test_loss += F.nll_loss(output, target, size_average=False).data[0] # sum up batch loss
        # �\�����x�������߂�
        pred = output.data.max(1, keepdim=True)[1] # get the index of the max log-probability
        # ���𗦂����߂�
        correct += pred.eq(target.data.view_as(pred)).cpu().sum()

    # ���ʂ��o��
    test_loss /= len(test_loader.dataset)
    print('\nTest set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)\n'.format(
        test_loss, correct, len(test_loader.dataset),
        100. * correct / len(test_loader.dataset)))


# args.epochs�������[�v����
for epoch in range(1, args.epochs + 1):
    # �P������
    train(epoch)
    # ���؂���
    test()


