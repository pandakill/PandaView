# PandaView
自定义viwe

1、封装好的顶部header栏,使用方法如下：
在xml文件中引入,例如在main_activity.xml,记得在布局文件中加入app命名空间
````
<RelativeLayout>
  <com.panda.view.PandaTopView
      android:layout_width="match_parent"
      android:layout_height="50dp"
      android:background="#177BAC"
      app:left_text="返回"
      app:left_textSize="14sp"
      app:left_textColor="#FFFFFF"
      app:title_text="标题栏"
      app:title_textSize="18sp"
      app:title_textColor="#FFFFF"/>
      
  <View
      android:layout_width="match_parent"
      android:layout_height="0.5dp"
      android:backgroud="#11FF00"/>
</RelativeLayout>
````
