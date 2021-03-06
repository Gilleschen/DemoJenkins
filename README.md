# 說明

#### Framework

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/framework.png)

#### 使用說明

* 安裝 Appium (http://appium.io/)

* 環境設定 (請參考<a href="http://www.qa-knowhow.com/?p=2363">Appium手機自動化測試從頭學 –Windows/Android環境安裝篇</a>)

* 下載<a href="https://github.com/Gilleschen/Appium_Auto_Testing/blob/master/TestScript.xlsm"> TestScript.xlsm</a>至C:\TUTK_QA_TestTool\TestTool資料夾

* 建立C:\TUTK_QA_TestTool\TestReport資料夾

* 腳本撰寫及Appium設定請參考<a href="https://github.com/Gilleschen/Appium_Auto_Testing_Android">Appium_Auto_Testing_Android</a>之腳本建立流程Step 1~Step 5

* *本專案無法序列測試多台裝置*

#### (1) Jenkins設定

Step 1. 安裝 <a href="https://jenkins-ci.org/">Jenkins</a>

Step 2. 進入 Global Tool Configuration, 設定JDK, Git, Ant, 如下圖：

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/Global%20Tool%20Configuration.png)

Step 3. 安裝 <a href="https://wiki.jenkins.io/display/JENKINS/Zephyr+For+Jira+Test+Management+Plugin">Zephyr for JIRA Test Management plugin</a>, 進入設定系統-> Zephyr For JIRA - Test Management Configuration->輸入JIRA Server, 	
 	User Name, Password, 如下圖：
  
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr%20_configurate.PNG)

#### (2) Zephyr設定

Step 1. 建立新 <a href="https://zephyrdocs.atlassian.net/wiki/spaces/ZTD/pages/3244044/Creating+and+Cloning+Test+Cycles">Zephyr Test Cycle</a>

#### (3) Jenkins作業組態設定

Step 1. 新增Jenkins作業

Step 2. 原始碼管理：點選「Git」, 輸入本專案網址如下圖

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/git.PNG)

Step 3. 建置：選擇「叫用Ant」, Target欄位輸入「Junit junitreport」如下圖

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/ant_2.PNG)

Step 4. 建置後動作：選擇「發佈JUnit測試報告結果」, 欄位填入「junit/*.xml」 如下圖

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/junit.PNG)

Step 5. 建置後動作：選擇「Publish test result to Zephyr for JIRA」

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr_2.PNG)

Step 6. 儲存

#### 備註：

* *目前不支援WiFi指令*

* Appium Client Libraries Version: java-client-6.1.0

* Selenium Client Version: 3.141.59

* 若腳本中使用Customized方法, 請於叫用Ant Target欄位最前方填入build，如下圖

![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/Ant%20build.PNG)

