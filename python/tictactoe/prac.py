# -*- coding: utf-8 -*- #

import tensorflow as tf

x = tf.constant(1, name='x')
y = tf.constant(2, name='y')

z = x + y

with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    sess.run(z)
    summary_writer = tf.summary.FileWriter('data', graph=sess.graph)
    tf.summary.scalar('one_plus_one_summary', z)

