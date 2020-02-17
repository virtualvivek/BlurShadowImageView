<h2 align="center"> <img src="https://github.com/vivekverma007/BlurShadowImageView/blob/master/preview/app_icon_title.jpg" width="400" /> </h2>

<p align="center">
 
 <a href="https://angularjs.org">
    <img src="https://img.shields.io/badge/Platform-Android-yellow.svg?color=419466"
      alt="Android" />
  </a>
  
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/badge/MinSdk-17-blue.svg"
      alt="MinSDK" />
  </a>
  
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/github/repo-size/vivekverma007/BlurShadowImageView.svg?color=e91e63"
      alt="MinSDK" />
  </a>

<a href="https://github.com/vivekverma007/BlurShadowImageView/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/vivekverma007/BlurShadowImageView.svg?color=blue"
      alt="License: MIT" />
  </a>
  
</p>

<p align="center">This library provides drop shdows to <b>ImageView</b> according to the image similar to <code>ios backdrop shadows</code>.This custom ImageView extends android ImageView to provide backdrop shadows effect.The similar shadow blurred effects can also be seen in <i>iOS Music App.</i></p>

<p align="center"><img src="https://github.com/vivekverma007/BlurShadowImageView/blob/master/preview/app_icon_demo.jpg" width="620" /> </p> 

## Installation
Add it in your root build.gradle at the end of repositories:
```js
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
```

Add the following dependency to your app build.gradle file:
```js
dependencies {
	        implementation 'com.github.vivekverma007:BlurShadowImageView:-SNAPSHOT'
	}
 
```


## How to use   
### Using Xml to config
```xml

 <me.virtualiz.blurshadowimageview.BlurShadowImageView
                android:layout_width="200dp"
                android:layout_height="220dp"
                android:layout_gravity="center"
                app:v_shadowOffset="40dp"  //sets offset b/w image and shadow
                app:v_imageRound="20dp"  //sets border radius
                app:v_imageSrc="@drawable/nature" />
```

###  Use Java code to config
```js
BlurShadowImageView blurshadowimageview = (BlurShadowImageView)findViewById(R.id.blurSImageView);

//Sets Border Round 
blurshadowimageview.setRound((int) value);  

//Sets Image Resource
blurshadowimageview.setImageResource(ImgRes);  

//Sets Image Drawable
blurshadowimageview.setImageDrawable(drawable);  

//Sets Image Bitmap
blurshadowimageview.setImageBitmap(bitmap);  

```

#### Xml attr 
 ##### SmoothRefreshLayout 
 |Name|Format|Defalut|
 |:---:|:---:|:---:|
 |app:v_imageRound|dimension|10dp|
 |app:v_imageSrc|reference|image|
 |app:v_shadowOffset|dimension|40dp|
 

## License

BlurShadowImageView is licensed under `MIT license`. View [license](https://github.com/vivekverma007/BlurShadowImageView/blob/master/LICENSE).<br>
Copyright (c) 2020 `@vivekverma007`
