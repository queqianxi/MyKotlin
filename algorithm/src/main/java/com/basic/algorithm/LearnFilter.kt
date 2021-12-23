package com.basic.algorithm

import java.util.*
import kotlin.collections.LinkedHashSet

/**
 * @author: Ww
 * @date: 2021/11/2
 */
/**字符串分割每8位，不够加0*/
fun addZero(){
    val scanner = Scanner(System.`in`)
    while(scanner.hasNext()){
        var text = scanner.next().toString()
        val a = text.length / 8
        val b = if(text.length % 8 > 0) 1 else 0
        val size = a + b
        if(text.length < 8 * size){
            for(i in 0 until 8 * size - text.length){
                text += "0"
            }
        }
        for(i in 0 until size * 8 step 8){
            val t = text.substring(i, i + 8)
            println(t)
        }
    }
}
//输入字母以及其他字符，字符串倒序
fun reverseCharacter(){
    val num = readLine().toString()
    val numBuilder = StringBuilder()
    val mat = Regex("[a-zA-Z]")
    for(s in num){
        if(s.toString().matches(mat)){
            numBuilder.append(s.toString())
        }else {
            numBuilder.append(" ")
        }
    }
    val last = numBuilder.toString().split(" ")
    val outNum = StringBuilder()
    for(s in last.size - 1 downTo 0){
        val text = last[s]
        outNum.append("$text ")
    }
    print(outNum)
}
//打印字符出现的次数
fun printTimes(){
    while (true){
        val text = readLine()
        val c = readLine()?.toUpperCase()
        val size = text?.length
        var count = 0
        if(size != null){
            for(s in 0 until size){
                val t = text[s]
                val tt = t.toString()
                if (tt.toUpperCase() == c) {
                    count++
                }
            }
        }
        println(count)
    }
}
fun reverse(){
    //反向
    val num = readLine().toString()
    val outNum = StringBuilder()
    for(s in num.length - 1 downTo 0){
        outNum.append(num[s].toString())
    }
    print(outNum)
}
//字符串去重、排序，字典排序可以用到compareTo
fun filterSort(){
    val s = Scanner(System.`in`)
    while (s.hasNext()){
        val set = TreeSet<Int>()
        val text = s.nextInt()
        for (i in 0 until text){
            set.add(s.nextInt())
        }
        for(i in set){
            println(i)
        }
    }
}
//输入一个 int 型整数，按照从右向左的阅读顺序，返回一个不含重复数字的新的整数。
//保证输入的整数最后一位不是 0 。
fun reverseNoRepeat(){
    val num = readLine().toString()
    val treeSet = LinkedHashSet<Int>()
    for(s in num.length - 1 downTo 0){
        treeSet.add(num[s].toString().toInt())
    }
    print(treeSet)
}
//正整数取平局，负数取个数
fun average(){
    val s = Scanner(System.`in`)
    while(s.hasNext()){
        val size = s.nextInt()
        var total = size
        var count = 0
        var sum = 0f
        for(i in 0 until size){
            val num = s.nextInt()
            if(num > 0){
                sum += num
            }else{
                if(num < 0){
                    count ++
                }
                total --
            }
        }
        val num = if(total > 0) String.format("%.1f", (sum / total)) else 0
        println("$count $num")
    }
}




