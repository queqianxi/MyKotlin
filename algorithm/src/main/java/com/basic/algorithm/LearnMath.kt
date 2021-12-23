package com.basic.algorithm

import java.util.*

/**
 * @author: Ww
 * @date: 2021/11/2
 */
//质因数求解，把数字180分界为2 2 3 3 5形式
fun primeFactorCompute(){
    var num = readLine()?.toLong()
    if(num != null){
        val n = kotlin.math.sqrt(num.toDouble()).toLong()
        for(i in 2 until n + 1){
            //一个正整数只有一个质因子是大于其平方根的
            while(num % i == 0L){
                print("$i ")
                num /= i
            }
        }
        print(if (num == 1L) " " else num)
    }
}
//输入一个 int 型的正整数，计算出该 int 型数据在内存中存储时 1 的个数。
fun calculateZero(){
    val `in` = Scanner(System.`in`)
    var num: Int = `in`.nextInt() //读取数字
    var n = 0 //计数变量
    for (i in 0..31) {
        if (num and 1 == 1) //如果末位为1则计数
            n++
        num = num ushr 1 //无符号右移
    }
    println(n)
}
//三个瓶子换一瓶饮料，如果有两瓶则借一瓶，喝完三个换一瓶还回去
var count = 0
fun calculateBottom(num : Int){
    val a = num / 3
    val b = num % 3
    if(a == 0 && b == 2){
        count++
        return
    }
    if(a == 0){
        return
    }
    count += a
    calculateBottom(a + b)
}
//斐波那契数列，1 1 2 3 5 8 13

//排身高，某一中间身高，左右都比他小，N个人问最小抽出几人
fun queueTall(){
    val scanner = Scanner(System.`in`)
    while(scanner.hasNext()){
        val size = scanner.nextInt()
        val arr = IntArray(size)
        val left = IntArray(size)
        val right = IntArray(size)
        for(i in 0 until size){
            arr[i] = scanner.nextInt()
        }
        for(i in 0 until size){
            left[i] = 0
            for(j in 0 until i){
                if(arr[j] < arr[i]){
                    left[i] = Math.max(left[j] + 1, left[i])
                }
            }
        }
        for(i in size - 1 downTo 0){
            right[i] = 0
            for(j in size - 1 downTo i){
                if(arr[j] < arr[i]){
                    right[i] = Math.max(right[j] + 1, right[i])
                }
            }
        }
        var maxInt = 0
        for(i in 0 until size){
            maxInt = Math.max(maxInt, left[i] + right[i] + 1)
        }
        println(maxInt)
    }
}
//求最大公约数或最小公倍数，更相减损术：辗转相除法
fun bigNum(){
    val scanner = Scanner(System.`in`)
    val num1 = scanner.nextInt()
    val num2 = scanner.nextInt()
    var outNum = 0
    outNum = if (num1 >= num2){
        bigNum1(num1, num2)
    }else{
        bigNum1(num2, num1)
    }
    print(outNum)
}
//辗转相除法
fun bigNum1(num1 : Int, num2 : Int) : Int{
    while(num1 % num2 > 0){
        return bigNum1(num2, num1 % num2)
    }
    return num2
}
//n * m个格子，只能向右和下，一共多少步走
fun step(){
    val scanner = Scanner(System.`in`)
    while(scanner.hasNext()){
        val num1 = scanner.nextInt()
        val num2 = scanner.nextInt()
        //看成坐标系，即由f(n,m)走到f(0,0)
        //f(n, m) = f(n - 1, m) + f(n, m -1)
        println(getNum(num1, num2))
    }
}
fun getNum(num1 : Int, num2 : Int) : Int{
    if(num1 == 0 || num2 == 0){
        return 1
    }
    return getNum(num1 - 1, num2) + getNum(num1, num2 - 1)
}