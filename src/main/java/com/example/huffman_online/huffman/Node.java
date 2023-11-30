package com.example.huffman_online.huffman;/*
 * Reference Huffman coding
 * 
 * Copyright (c) Project Nayuki
 * MIT License. See readme file.
 * https://www.nayuki.io/page/reference-huffman-coding
 */


/**
 * A node in a code tree.
 * @see CodeTree
 */
public sealed interface Node permits InternalNode, Leaf {}
