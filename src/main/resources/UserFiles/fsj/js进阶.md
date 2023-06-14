#  js进阶

## 1.作用域&解构&箭头函数

### 1.1作用域

> 作用域（scope）：规定变量的访问范围

#### 局部作用域

> 函数作用域->函数内部声明的变量，函数外部无法访问，函数执行完毕后变量实际被回收清空

> 块作用域-> 由{}包含代码称为代码块，代码块内部声明的变量，外部有可能不能访问
>
> ```javascript
> for(let t=1;t<4;t++){
>     //t只能在该代码块访问
>     console.log(t)
> }
> console.log(t)//无法访问
> ```
>
> 注：使用var声明变量不会产生块作用域，即外部可访问
>
> let和const声明的会产生块作用域

#### 全局作用域

> **<script>**标签和 .js文件的最外层
>
> 注：为window对象动态添加的属性默认是全局
>
> 函数中未使用任何关键字声明的变量为全局变量

#### 作用域链

> 本质上是底层的变量查找机制
>
> > 函数被执行时优先查找当前函数作用域，如果当前作用域找不到则会依次逐级查找父级作用域直到全局作用域，（同作用域链串按照从小到大的顺序查找，子作用域可以访问父级，父级不可访问子级）

#### 垃圾回收机制

> 内存生命周期

内存分配：声明变量，函数，对象时，系统自动为其分配内存

内存使用：读写内存，即使用变量，函数等

内存回收：使用完毕，由垃圾回器自动回收不适用的内存

注：全局变量一般不会回收（关闭页面时才回收），局部变量使用完毕便自动回收

内存泄漏：程序中分配的内存由于某种原因程序未释放或无法释放

> 引用计数算法

IE采用引用计数算法，定义“内存不再使用”，就是看一个对象是否有指向它的引用，没有引用就回收对象

算法：跟踪记录被引用的次数，被引用一次，记录次数加一，减少一个引用就减一，次数为0，则释放内存

存在的问题：嵌套引用（循环引用）时垃圾回收器不会进行回收

例：

>```javascript
>function fn(){
>	let o1={}
>	let o2={}
>	o1.a=o2
>	o2.a=o1
>	return '引用计数无法回收'
>}
>```

> 标记清除法

从根部（js全局对象）出发定期查找，凡是能找到均标记为使用，找不到便标记为未使用，进而回收

#### 闭包

> 概念：一个函数对周围状态的引用捆绑在一起，内层函数中访问到其外层函数的作用域
>
> 闭包=内层函数+外层函数的变量（单纯内外函数不会产生闭包，内部函数调用外部函数时产出闭包）

例：

>```javascript
>function out(){
>	let a=10
>	function in(){
>		console.log(a)
>	}
>}
>```

> 闭包的作用：封闭数据（实现数据私有），提供方法使外部可以访问函数内部的变量
>
> 可能引起内存泄漏

#### 变量提升

> 允许变量未定义便被引用（仅存在于var声明），代码执行前会将var声明的变量提到当前作用域的最前面，只提升声明，不提示赋值

### 1.2函数进阶

#### 函数提升

> 代码执行时，把所有函数声明提到当前作用域的最前面，即函数调用语句可以放在声明前

#### 函数参数

> 动态参数
>
> arguments属性：伪数组

例：

```javascript
function getSum(){
let sum=0
	for(let i=0;i<arguments.length;i++)
		sum+=arguments[i]
}
getSum(1,2,3)
```

> 剩余参数：允许将一个不定数量的参数表示为一个数组，获取到的数据为真数组
>
> 语法：function fun(...arr){}
>
> ...语法符号，至于最末函数形参之前，用于获取剩余的参数

例：

```javascript
function getSum(a,b,...arr){//允许用户输入至少两个参数，剩下的参数作为一个数组获取
    console.log(arr)//输出[3,4,5]
}
getSum(1,2,3,4,5)
```

> 展开运算符  ...arr，将一个数组展开，传递参数时展开数据使用，典型运用：求数组最大（小）值、合并数组

例：

```javascript
const arr=[1,2,3,4,5]
console.log(Math.max(...arr))//5
console.log(Math.min(...arr))//1
```

剩余参数：函数参数中使用，得到真数组

展开运算符：数组中使用，数组展开

#### 箭头函数

> 更简短的函数写法并且不绑定this，箭头函数的语法比函数表达式更简洁（适用于本来需要匿名函数的地方）

基本语法：

```javascript
const fn=(x)=>{
    console.log(x)
}
//只有一个参数时可以省略小括号
const fn= x =>{
    console.log(x)
}
//只有一行代码时可以省略大括号
const fn= x =>console.log(x)
//只有一行代码时可以省略return
const fn= x =>{
    return x+x
}
const fn= x => x+x
//箭头函数可以返回对象：加括号的函数体返回对象字面量表达式
const fn= uname =>({name:uname})
console.log(fn('pink'))//{name:'pink'}
```

箭头函数参数：没有动态参数arguments，但是能够使用剩余参数...arr

箭头函数this：箭头函数不会创建自己的this，它只会从作用域链的上一级沿用this

DOM事件回调函数使用箭头函数时 this指向全局的window

### 1.3解构赋值

#### 数组解构

> 赋值：

> 快速的将数组单元值赋值给一系列变量
>
> 语法[]=[]：左侧的[]用于批量声明变量，右侧数组的单元值将被依次赋值给左侧的变量

例：

```javascript
const [max,min,avg]=[100,60,80]
//等价于const max=100
const min=60.....
```

> 变量交换：交换两个变量的值
>
> ```javascript
> let a=1
> let b=2;//添加分号;
> [b,a]=[a,b]//交换a,b 的值
> ```

必须添加分号的两种情况

> 1.立即执行函数
>
> 2.使用数组的时候

#### 对象解构

> 将对象属性和方法快速赋值给一些列变量

> 语法：
>
> ```javascript
> const {uname, age} ={uname: 'pink', age: 18}
> 相当于
> const uname=obj.uname
> 对变量名进行修改
> const {uname: username, age} ={uname: 'pink', age: 18}
> ```
>
> 注：对象属性的值将被赋值给与属性名相同的变量

#### 数组对象解构

```javascript
const pig=[
    {
        uname:'佩奇',
        age: 10
    }
]
解构
const [{uname,age}]=pig
```

多级解构

```javascript
const [{uname,age,family:{mother,father}}]=[{
        uname:'pig',
        age: 18,
        family:{
            mother:11,
            father:77
        }
    }]
```

#### forEach()方法

> 调用数组每个元素，并将元素传递给回调函数
>
> 适用于遍历数组对象

语法：

```javascript
arr.forEach(function(数组元素, 索引号){
    //函数体,索引号可以省略
})
```

#### filter()方法

> 创建一个新数组，新数组中的元素是通过检查指定数组中符合条件的所有元素

语法：

```javascript
const newArr=arr.filter(function(数组元素，索引号){
    函数体
})
```



## 2.构造函数&数据常用函数

### 2.1深入对象

#### 创建对象的三种方式

>1.利用对象字面量创建
>
>2.通过new object()创建
>
>3.通过构造函数创建

```javascript
const obj={
    //字面量
}
const obj=new Object()
obj.name='pig'
const obj=new Object({neme:'pig'}
```

#### 构造函数

> 一种特殊的函数，主要用于初始化对象
>
> 使用场景：快速创建多个类似对象，将相同属性抽取出来，并封装在一个函数内

例：

```javascript
function Pig(name,age,gender){//两个约定：函数名开头大写，通new关键字创建新对象（实例化对象/构造函数）
    this.name=name
    this.age=age
    this.gender=gender
}
const pig=new pig('p',6,'女')
```

说明：函数内部无需写return，返回值为新创建的对象，构造函数内部的return返回值无效，故不需要return

> 实例化执行过程

> 创建新空对象->构造函数this指向新对象->执行构造函数代码，修改this，添加新属性->返回新对象

#### 实例成员&静态成员

实例

> 通过构造函数创建按的对象称为实力对象，实例对象中的属性和方法称为实例成员（实例属性和实例方法）

> 为构造函数传入参数，创建结构相同但值不同的对象，构造函数创建的实例对象互不影响

静态

> 构造函数的属性和方法称为静态成员（静态成员只能通过构造函数访问）

### 2.2 内置构造函数

javascript基本数据类型：字符串、数值、布尔、undefined、null

引用类型：对象

js底层简单数据类型会

> 内置构造函数
>
> 引用类型：Object，Array，Date等
>
> 包装类型：String，Number，Boolean等

#### Object 

> 用于创建普通对象

三个常用静态方法

```javascript
Object.keys(obj)//返回数组[属性名1，属性名2]
Object.values(obj)//返回数组[属性值1，属性值2]
Object.assign(obj,o)//拷贝对象(o->obj)，合并一个或多个对象到目标对象
```

#### Array

> 用于创建数组

```javascript
const arr=new Array(1,2)//[1,2]
```

reduce基本语法

```javascript
arr.reduce(function(){},初始值)
arr.reduce(function(上一次值,当前值){},初始值)
//如果有初始值，将初始值作为第一个元素（上一次值）加进入运算，如果没有初始值则第一个元素为初始值，
//每一次循环将返回值作为下一次循环的上一次值
const arr=[1,2,3]
arr.reduce(function(cu,m){
    return cu+m//1+2=3 =>3+3=6
})
```

伪数组转换为真数组

```javascript
Array.form(伪数组)
```

数组常见实例方法

| 方法    | 作用         | 说明                                 |
| ------- | ------------ | ------------------------------------ |
| forEach | 遍历数组     | 不返回数组，常用于查找遍历数组元素   |
| filter  | 过滤数组元素 | 返回新数组（满足筛选条件的数组元素） |
| map     | 迭代数组     | 返回新数组，经处理后的数组元素       |
| reduce  | 累计器       | 返回累计处理的结果，常用于求和等     |

![image-20230523171018810](C:\Users\31749\AppData\Roaming\Typora\typora-user-images\image-20230523171018810.png)

#### String

![image-20230523173151732](C:\Users\31749\AppData\Roaming\Typora\typora-user-images\image-20230523173151732.png)

#### Number

> 用于创建数值

toFixed()方法：让数字保留指定位数

## 3.深入面向对象

### 3.1编程思想

#### 面向过程

> 分析出解决问题所需的步骤，然后用函数把这些步骤一步一步实现，使用时再一个一个依次调用

#### 面向对象

> 把事务分解成一个一个对象，然后由对象间分工与合作

> 封装继承多态

![image-20230524125314392](C:\Users\31749\AppData\Roaming\Typora\typora-user-images\image-20230524125314392.png)

### 3.2 构造函数

通过构造函数封装

### 3.3 原型

#### 原型对象

> 构造函数通过原型分配的函数是所有对象所共享的
>
> js规定：每一个构造函数都有prototype属性，指向另一个对象，称为原型对象
>
> 原型对象可以挂载函数，对象实例化时不会重复创建函数，节约内存空间
>
> 构造函数和原型对象中的this都指向实例对象

```javascript
function Star(uname,age){
    this.uname=uname
    this.age=age//在构造函数内部创建函数每次实例化会创建新的函数方法，挂载在原型上可以避免同一个方法的重复创建
    this.sing=function(){
        console.log('唱歌')
    }
}
Star.prototype.sing=function(){
    console.log('唱歌')
}
```

#### constructor属性

> 该属性指向原型对象的构造函数

构造函数prototype属性指向原型对象，原型对象constructor属性指向构造函数

> 采用对象形式对prototype属性赋值时会覆盖原型对象，需要使用constructor重新指向构造函数

```javascript
Star.prototype={
    constructor: Star,
    sing: function(){
        console.log('唱歌')
    },
    dance: function(){
        console.log('跳舞')
    }
}
```

#### 对象原型

> 对象都有一个属性**__**proto**__**指向构造函数的prototype原型对象

注：

> **__**proto**__**是js非标准属性，只读属性，不可重新赋值
>
> **__**proto**__**与[prototype]意义相同
>
> 用于表明当前实例对象指向哪个原型对象prototype
>
> **__**proto**__**对象原型里面也有一个constructor属性，指向创建该实例对象的构造函数

#### 原型继承

将公共属性方法抽取出来放在公共属性中，将其赋值给原型

```javascript
const people={
    eye: 2,
    mouse: 1
}
function Man(){
    
}
Man.prototype=people//赋值会覆盖原prototype属性，所以需要紧跟对constructor属性的重新赋值
Man.prototype.constructor=Man
```

将公共属性方法抽取出来放在公共构造函数中，将其赋值给原型

```javascript
function People(){
    eye: 2,
    mouse: 1
}
function Man(){
    
}
Man.prototype=new People()//赋值会盖原prototype属性，所以需要紧跟对constructor属性的重新赋值
Man.prototype.constructor=Man
```

类似于Java父类与子类的继承关系，父级构建函数是子级构造函数的原型函数

#### 原型链

基于原型对象的继承使得不同构造函数的原型对象串联在一起，并且这种关联的关系是一种链状结构，称为原型链

![image-20230524211715036](C:\Users\31749\AppData\Roaming\Typora\typora-user-images\image-20230524211715036.png)

![image-20230524212449483](C:\Users\31749\AppData\Roaming\Typora\typora-user-images\image-20230524212449483.png)



## 4.高阶技巧

### 4.1 深浅拷贝

> 深浅拷贝只针对引用类型

#### 浅拷贝

>拷贝地址：单层对象的值及嵌套对象的地址
>
>```javascript
>obj={
>    age: 18,//拷贝属性值
>    ac: {//拷贝属性地址
>        str: 1243
>    }
>}
>```
>
>

常见方法：

> ```javascript
> 拷贝对象：Object.assgin(obj)//或展开运算符{...obj}拷贝对象
> 拷贝数字：Array.prototype.concat(arr)//或咱开运算符[...arr]
> ```

#### 深拷贝

常见方法：

通过递归实现深拷贝

```javascript
const obj={
    uname: 'pink',
    age: 18,
    hobby: ['乒乓球','足球'],
    family: {
        baby: '小pink'
    }
}
const o={}
//拷贝函数
function deepCopy(newObj,oldObj){
    for(let k in oldObj){
        //k:属性名  oldObj[k]属性值
        //处理数组问题
        if(oldObj[k] instanceof Array){
            newObj[k]=[]//接收[] hobby
            			//oldObj[k]  ['乒乓球','足球']
            deepCopy(newObj[k],oldObj[k])
        }else if(oldObj[k] instanceof Object){
            newObj[k]={}//接收{} family
            			//oldObj[k]  '小pink'
            deepCopy(newObj[k],oldObj[k])
        }else{
            //k 属性名 uname age    oldObj[k]  属性值 18
            //newObj[k] ===o.uname  给新对象添加属性
            newObj[k]=oldObj[k]
        }
    }
}
```

方法2

通过JSON字符串转换

```javascript
const o=JSON.parse(JSON.stringify(obj))
```

方法3

利用js库lodash中的_.cloneDeep()方法

### 4.2 异常处理

#### throw抛异常

```javascript
function fn(x,y){
    if(!x|| !y){
        throw '没有参数传递进来'//throw会终止程序
        throw new Error('没有参数传递进来')//异常信息更详细
    }
}
```

#### try/catch捕获异常

```javascript
function fn(){
    try {//拦截错误，提示浏览器提供的错误信息，不会中断程序
        const p= document.querySelector('.p')
        p.style.color= 'red'
    }catch(err){//捕获异常信息
        console.log(err.message)
    }finally{
        //不论程序是否错误，一定会执行的代码
        alert('弹出对话框')
    }
}
```

#### debugger调试

> 类似于打断点，代码运行时直接跳转到debugger断点位置

### 4.3 处理this

#### this指向

普通函数this指向：调用方式决定了this的值，即指向调用者，无明确调用者时指向window，严格模式下没有调用者时this的值为null

箭头函数this指向：不受调用方式的影响，事实上箭头函数没有this，箭头函数会默认绑定外层this的值，即箭头函数中的this引用的就是最近（一层一层向外找，直到找到this为止）作用域中的this

注：

> 事件回调函数使用箭头函数时，this为全局的window，因此DOM事件回调函数如果需要DOM对象的this则不适宜使用箭头函数
>
> 同理构造函数、原型对象不适用箭头函数

#### 改变this

改变this的指向

> call()

```javascript
fun.call(thisArg,arg1,arg2,...)
thisArg:在fun函数运行时指定的this值
arg1，arg2：传递的其他参数
返回值为函数的返回值，因其本身就是调用函数（调用fun）
```



> apply()

```javascript
fun.apply(thisArg,[argsArray])
thisArg:在fun函数运行时指定的this值
argsArray:传递的值，须包含在数组中
返回值为函数的返回值，因其本身就是调用函数（调用fun）
```

> bind()

```javascript
fun.bind(thisArg,arg1,arg2,...)
thisArg:在fun函数运行时指定的this值
arg1，arg2：传递的其他参数
返回值由指定的this值华人初始化参数改造的原函数拷贝（新函数）
bind()方法不会调用函数。
```

### 4.4 防抖与节流

#### 防抖(debounce)

> 单位事件内，频繁触发事件，只执行最后一次

1.利用js库lodash中的_.debounce()方法

2.利用setTimeout实现

> 1.声明一个定时器变量，
>
> 2.鼠标每次滑动都先判断是否由定时器，
>
> 3.如果有就先清除再另起定时器，
>
> 4.定时器中写函数调用

```javascript
function mouseMove(){
    
}
function debounce(fn,t){
    let timer //1.
    return function(){//2.3.4
        if(timer) clearTimeout(timer)
        timer=setTineout(function(){
            fn()
        },t)
    }//将匿名函数返回给debounce(mouseMove,500)
}
box.addEventListener('mousemove',debounce(mouseMove,500))
```

#### 节流

> 单位事件内，频繁触发事件，只执行第一次

1.利用js库lodash中的_.throttle()方法

2.利用setTimeout实现

> 1.声明一个定时器变量，
>
> 2.鼠标每次滑动都先判断是否由定时器，如果有则不开启新定时器，
>
> 3.如果没有则开启新定时器，
>
> 4.定时器中写函数调用

```javascript
function mouseMove(){
    
}
function throttle(fn,t){
    let timer //1.
    return function(){//2.3.4
        if(!timer) {
        	timer=setTineout(function(){
            fn()
                //清空定时器
            timer=null
            }
        },t)
    }//将匿名函数返回给throttle(mouseMove,500)
}
box.addEventListener('mousemove',throttle(mouseMove,500))
```

视频事件：

ontimeupdate  事件在视频/音频（audio/video）当前的播放位置发生改变时触发

onloadeddata  事件在当前帧的数据加载完成且还没有足够的数据播放视频/音频（audio/video）的下一帧时触发（打开页面时）
