# 簡介

輕量的購物網站，業主能選擇指定銷售日自行上架商品，用戶能下單並追蹤訂單進度
> 網站內並不包含實際金流的處理

## 使用工具

* IDE - Idea IntelliJ
* 前端 - BootStrap 3
* 前端模板 - Thymeleaf
* 後端 - Spring Boot 3
* 關聯資料庫 - Mysql + Mybatis + Mybatis Plus
* 非關聯資料庫 - Redis
* Google 社交登錄  

## 運行前請注意
請將 Application.properties 中的 spring.sql.init.mode=always 打開，以便自動創建所有Mysql表，但是在下次重啟前必須關閉，否則會有刪除已創建之表的風險

## 運行方式

1. 下載
2. 替換配置文件(application-dev.properties)中各服務的帳號、密碼等私密訊息
3. 運行



## 使用影片

https://github.com/day20180721/bakery/assets/44454920/ef7260f4-de98-416b-b78e-c6f181dce692

## 如何新建一個新頁面 ?

我定義了兩種BaseTemplate，分別是customer與business，可依照該頁面的功能從兩者中選一，Basetemplate裡面會導入一些基礎的 js、css、head-meta ，只要使用 Thymeleaf 引入模板即可快速建立頁面



https://github.com/day20180721/bakery/assets/44454920/70c75d8f-c60c-4d41-a818-2beb0f391272



## 新建一張表後如何速成 Service 中的 CRUD ?
1. ##### 創建對應之DTO
 
https://github.com/day20180721/bakery/assets/44454920/f758e635-66f5-469e-89fd-17e1f16bb723

2. ##### 創建Service、Mapper、MapperXml

https://github.com/day20180721/bakery/assets/44454920/4c9f1609-0cc7-4151-827f-c2d3d8af7078

## 重要檔案說明

### logback-spring

##### 配置在不同環境下的 Log 輸出級別及位置

### application-*.properties

##### *可以是任一名稱，如 dev 環境 、product 環境

`指定方法 : 在application.properties中 - spring.profiles.active=dev`

## Log說明

logger name="com.littlejenny.bakery.dao.mysql" level="debug"
代表該包中的全部的Spring Entity都會輸出指定級別以上的log的訊息

### 重要程度(低 - 高)

1. debug
2. warn
3. error

## Application.properties 參數說明

### Mysql

##### 允許一次執行多個操作 - allowMultiQueries=true

### Mybatis

##### 開啟駝峰命名 - mybatis.configuration.map-underscore-to-camel-case=true

## 命名規範

### Mysql

##### https://www.sqlstyle.guide/zh-tw/#%E8%A1%A8%E6%A0%BCtables

### 包名

##### https://www.jianshu.com/p/f046cc7889c5

## 註釋 - 啟用特定服務說明

### @EnableTransactionManagement

##### 執行 Spring Entity 的方法時，若其方法上有 @Transaction ，則會有事務的效果

### @EnableRedisHttpSession

##### HttpServletRequest 的 Session 存與取操作會從指定的 RedisServer 實現

### @MapperScan

##### 指定 MyBatis 的 Mapper 放置路徑

### @Slf4j

##### 若在 Spring Entity 的類上有 @Slf4j 則方法內擁有 log.debug、warn、error 等功能

## 註釋 - 處理請求說明

### RequestBody

##### 接收以JSON格式傳遞的參數，常用於Ajax - Post

### RequestParam

##### 接收表單參數，依照表單各個Input - name

### ResponseBody

##### 指定回傳的數據為 Json，常用於 Ajax

## 程式語法細節

### Html

##### data-*，*的字串內只能是小寫

##### 請求127.0.0.1與localhost所使用的cookie不相同，所以用戶資訊兩者不同步

### Javascript

##### type為module的javascript，即使沒有調用裡面的function，也會執行一次

##### jquery - on.(event,function)中所傳遞的function必須含有參數(event)

##### ajax - data須注意是否已經JSON.stringify()

### Java

##### new Date(Year,Month)，month如果為11，則會顯示12月

##### Paths.get("123.txt")

* Jar - 與Jar包同層的目錄新建123.txt
* 編輯器 - 與src同層的目錄新建123.txt  
  `如果Path為txts/123.txt，則要先建立該txts目錄，否則無效`

### Mybatis

##### Mybatis原生的save update才支援TableID type

## 後續提升
* FileController獨立成Application
* AuthController獨立成Application







