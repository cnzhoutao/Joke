1.这个项目采用了网上一个笑话的api，每天会推送20条笑话

2.界面采用MD风格，用CardView实现流式布局

3.通过Spanned sp = Html.fromHtml( joke.getContent() );
实现TextView显示带标签的文本内容

4.使用Rxjava2+retrofit2实现网络请求

5.解决了第一次打开时必须要下拉刷新才能显示数据的问题

    compile 'io.reactivex.rxjava2:rxjava:2.0.7'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'com.squareup.retrofit2:retrofit:2.2.0'
    compile 'com.squareup.retrofit2:converter-gson:2.2.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'

整体效果
![image](https://github.com/HelloNanKe/Joke/blob/master/screenG.gif)
