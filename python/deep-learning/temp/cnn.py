# -*- coding: utf-8 -*- #
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data

#mnist読み込み
mnist = input_data.read_data_sets('MNIST_data', one_hot=True)
#InteractiveSessoin作成
sess = tf.InteractiveSession()

#重み変数の初期化
def weight_variable(shape):
    initial = tf.truncated_normal(shape, stddev=0.1)
    return tf.Variable(initial)

#バイアスの初期化
def bias_variable(shape):
    initial = tf.constant(0.1, shape=shape)
    return tf.Variable(initial)

#畳み込み演算
def conv2d(x, W):
      return tf.nn.conv2d(x, W, strides=[1, 1, 1, 1], padding='SAME')

#2 x 2のMAXプーリング
def max_pool_2x2(x):
    return tf.nn.max_pool(x, ksize=[1, 2, 2, 1], strides=[1, 2, 2, 1], padding='SAME')

#x:入力値,y_:出力値のplaceholderセット
x = tf.placeholder(tf.float32, shape=[None, 784])
y_ = tf.placeholder(tf.float32, shape=[None, 10])

#mnistは1次元でデータを返すので、28x28x1にreshape
#[バッチ数、縦、横、チャンネル数]
x_image = tf.reshape(x, [-1,28,28,1])
#畳み込み層１（フィルター数は32個）
#フィルターのパラメタをセット
#[縦、横、チャンネル数、フィルター数]
W_conv1 = weight_variable([5, 5, 1, 32])
#32個のバイアスをセット
b_conv1 = bias_variable([32])
#畳み込み演算後に、ReLU関数適用
h_conv1 = tf.nn.relu(conv2d(x_image, W_conv1) + b_conv1)
#2x2のMAXプーリングを実行
#2x2のMAXプーリングをすると縦横共に半分の大きさになる
h_pool1 = max_pool_2x2(h_conv1)

#畳み込み層２（フィルター数は64個）
#フィルターのパラメタをセット
#チャンネル数が32なのは、畳み込み層１のフィルター数が32だから。
#32個フィルターがあると、出力結果が[-1, 28, 28, 32]というshapeになる。
#入力のチャンネル数と重みのチャンネル数を合わせる。
W_conv2 = weight_variable([5, 5, 32, 64])
b_conv2 = bias_variable([64])
h_conv2 = tf.nn.relu(conv2d(h_pool1, W_conv2) + b_conv2)
h_pool2 = max_pool_2x2(h_conv2)

#全結合層（ノードの数は1024個）
#2x2MAXプーリングを2回やってるので、この時点で縦横が、28/(2*2)の7になっている。
#h_pool2のshapeは、[-1, 7, 7, 64]となっているので、7*7*64を入力ノード数とみなす。
W_fc1 = weight_variable([7 * 7 * 64, 1024])
b_fc1 = bias_variable([1024])
#全結合層の入力仕様に合わせて、2次元にreshape
h_pool2_flat = tf.reshape(h_pool2, [-1, 7*7*64])
h_fc1 = tf.nn.relu(tf.matmul(h_pool2_flat, W_fc1) + b_fc1)

#ドロップアウト処理
#keep_probは、ドロップアウトさせない率
keep_prob = tf.placeholder(tf.float32)
h_fc1_drop = tf.nn.dropout(h_fc1, keep_prob)

#出力層
W_fc2 = weight_variable([1024, 10])
b_fc2 = bias_variable([10])
y_conv = tf.matmul(h_fc1_drop, W_fc2) + b_fc2

#softmaxと交差エントロピーを合わせてやってくれるやつっぽい。あとで確認する。
cross_entropy = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(labels=y_, logits=y_conv))
#AdamOptimizerの学習率を0.0001に設定している。デフォルトが0.001だが、
#複雑なモデルになるとより小さくしないと発散してうまくいかないケースがあるらしい。
train_step = tf.train.AdamOptimizer(1e-4).minimize(cross_entropy)
correct_prediction = tf.equal(tf.argmax(y_conv,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))
sess.run(tf.global_variables_initializer())

for i in range(20000):
  batch = mnist.train.next_batch(50)
  if i%100 == 0:
    train_accuracy = accuracy.eval(feed_dict={
        x:batch[0], y_: batch[1], keep_prob: 1.0})
    print("step %d, training accuracy %g"%(i, train_accuracy))
  train_step.run(feed_dict={x: batch[0], y_: batch[1], keep_prob: 0.5})

print("test accuracy %g"%accuracy.eval(feed_dict={
    x: mnist.test.images, y_: mnist.test.labels, keep_prob: 1.0}))
