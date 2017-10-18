# -*- coding: utf-8 -*- #

# ---------- Package Import ---------- #
import tensorflow as tf
import numpy as np

from tensorflow.examples.tutorials.mnist import input_data
mnist = input_data.read_data_sets("./../MNIST_data/", one_hot=True)


# ---------- Define Function ---------- #
def inference(input_placeholder):
    W = tf.Variable(tf.zeros([784, 10]))
    b = tf.Variable(tf.zeros([10]))
    y = tf.nn.softmax(tf.matmul(input_placeholder, W) + b)

    return y

def loss(output, supervisor_labels_placeholder):
    cross_entropy = -tf.reduce_sum(supervisor_labels_placeholder * tf.log(output))
    return cross_entropy


def training(loss):
    train_step = tf.train.GradientDescentOptimizer(0.01).minimize(loss)
    return train_step




# ---------- Main Routine ---------- #
input_placeholder = tf.placeholder("float", [None, 784])
supervisor_labels_placeholder = tf.placeholder("float", [None, 10])


with tf.Session() as sess:
    output = inference(input_placeholder)
    loss = loss(output, supervisor_labels_placeholder)
    training_op = training(loss)

    saver = tf.train.Saver()
    ckpt = tf.train.get_checkpoint_state('./')
    if ckpt:
        last_model = ckpt.model_checkpoint_path
        print("load " + last_model)
        saver.restore(sess, last_model)
    else:
        init = tf.global_variables_initializer()
        sess.run(init)

    for step in range(1000):
        batch_xs, batch_ys = mnist.train.next_batch(100)
        feed_dict = {input_placeholder: batch_xs, supervisor_labels_placeholder: batch_ys}
        sess.run(training_op, feed_dict=feed_dict)
        if step % 100 == 0:
            correct_prediction = tf.equal(tf.argmax(output, 1), tf.argmax(batch_ys, 1))
            accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))
            print(sess.run(accuracy, feed_dict=feed_dict))

    saver.save(sess, "temp01.ckpt")

