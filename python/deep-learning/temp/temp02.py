# -*- coding: utf-8 -*- #

## ---------- Import ----------#
#import tensorflow as tf
#import numpy as np
#
#from tensorflow.examples.tutorials.mnist import input_data
#mnist = input_data.read_data_sets("./../MNIST_data", one_hot=True)
#
#
## ---------- Function Define ---------- #
#def mlp(x, output_dim, reuse=False):
#    w1 = tf.get_variable("w1", [x, get_shape()[1], 1024], initializer=tf.random_normal_initializer())
#    b1 = tf.get_variable("b1", [1024], initializer=tf.constant_initializer(0.0))
#    w2 = tf.get_variable("w2", [])
# # import

# In[1]:

import tensorflow as tf
import numpy as np
from tensorflow.examples.tutorials.mnist import input_data


# # load dataset

# In[2]:

mnist = input_data.read_data_sets("./data/mnist/", one_hot=True) 


# # build model

# In[3]:

def mlp(x, output_dim, reuse=False):

    w1 = tf.get_variable("w1", [x.get_shape()[1], 1024], initializer=tf.random_normal_initializer())
    b1 = tf.get_variable("b1", [1024], initializer=tf.constant_initializer(0.0))
    w2 = tf.get_variable("w2", [1024, output_dim], initializer=tf.random_normal_initializer())
    b2 = tf.get_variable("b2", [output_dim], initializer=tf.constant_initializer(0.0))

    fc1 = tf.nn.relu(tf.matmul(x, w1) + b1)
    fc2 = tf.matmul(fc1, w2) + b2

    return fc2, [w1, b1, w2, b2]

def slp(x, output_dim):
    w1 = tf.get_variable("w1", [x.get_shape()[1], output_dim], initializer=tf.random_normal_initializer())
    b1 = tf.get_variable("b1", [output_dim], initializer=tf.constant_initializer(0.0))

    fc1 = tf.nn.relu(tf.matmul(x, w1) + b1)
    return fc1, [w1, b1]

n_batch = 32
n_label = 10
n_train = 10000
imagesize = 28
learning_rate = 0.5

x_node = tf.placeholder(tf.float32, shape=(n_batch, imagesize*imagesize))
y_node = tf.placeholder(tf.float32, shape=(n_batch, n_label))

with tf.variable_scope("MLP") as scope:
    out_m, theta_m = mlp(x_node, n_label)

loss = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(out_m, y_node))
opt  = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss)
tr_pred = tf.nn.softmax(out_m)

test_data = mnist.test.images
test_labels = mnist.test.labels
tx = tf.constant(test_data)
ty_ = tf.constant(test_labels)

with tf.variable_scope("MLP") as scope:
    scope.reuse_variables()
    ty, _ = mlp(tx, n_label)

te_pred = tf.nn.softmax(ty) 


# In[4]:

def accuracy(y, y_):
    return 100.0 * np.sum(np.argmax(y, 1) == np.argmax(y_, 1)) / y.shape[0]


# In[5]:

saver = tf.train.Saver()

sess=tf.InteractiveSession()

init = tf.global_variables_initializer()
sess.run(init)


# In[6]:

for step in xrange(n_train):
    bx, by = mnist.train.next_batch(n_batch)
    feed_dict = {x_node: bx, y_node: by}
    _, _loss, _tr_pred = sess.run([opt, loss, tr_pred], feed_dict=feed_dict)
    if step % 500 == 0:
        _accuracy = accuracy(_tr_pred, by)
        print ('step = %d, loss=%.2f, accuracy=%.2f' % (step, _loss, _accuracy))

print ('test accuracy=%.2f' % accuracy(te_pred.eval(), test_labels))


# In[8]:

old_theta_m = [ p.eval() for p in theta_m] # for comparing


# In[9]:

saver.save(sess, "model.ckpt")


# In[10]:

sess.close()


# In[11]:

sess=tf.InteractiveSession()

# for clear
init = tf.initialize_all_variables()
sess.run(init)


# In[12]:

for prm, prm_o in zip(theta_m, old_theta_m):
    p1 = prm.eval()
    p2 = prm_o
    print (np.sum(p1 != p2))


# In[13]:

saver.restore(sess, "model.ckpt")


# In[14]:

for prm, prm_o in zip(theta_m, old_theta_m):
    p1 = prm.eval()
    p2 = prm_o
    print (np.sum(p1 != p2))


# In[15]:

print ('test accuracy=%.2f' % accuracy(te_pred.eval(), test_labels))


# In[16]:

for step in xrange(n_train):
    bx, by = mnist.train.next_batch(n_batch)
    feed_dict = {x_node: bx, y_node: by}
    _, _loss, _tr_pred = sess.run([opt, loss, tr_pred], feed_dict=feed_dict)
    if step % 500 == 0:
        _accuracy = accuracy(_tr_pred, by)
        print ('step = %d, loss=%.2f, accuracy=%.2f' % (step, _loss, _accuracy))

print ('test accuracy=%.2f' % accuracy(te_pred.eval(), test_labels))


# In[17]:

sess.close()


# In[ ]:

tf.reset_default_graph()
