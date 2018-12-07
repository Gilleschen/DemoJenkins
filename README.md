# Welcome

#### 使用前須知：
* 測試腳本撰寫及Appium相關設定請參考<a href="https://github.com/Gilleschen/Appium_Auto_Testing_Android">Appium_Auto_Testing_Android</a>

* 本專案無法序列測試多台裝置

#### (1) Jenkins設定
1. 安裝 <a href="https://jenkins-ci.org/">Jenkins</a>
2. 進入 Global Tool Configuration (設定JDK, Git, Ant, 如下圖)
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/Global%20Tool%20Configuration.png)
3. 於Jenkins安裝 <a href="https://wiki.jenkins.io/display/JENKINS/Zephyr+For+Jira+Test+Management+Plugin">Zephyr for JIRA Test Management plugin</a> (進入設定系統-> Zephyr For JIRA - Test Management Configuration->輸入JIRA Server, 	
 	User Name, Password, 如下圖)
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr%20_configurate.PNG)

#### (2) Zephyr設定
1. 建立新 <a href="https://zephyrdocs.atlassian.net/wiki/spaces/ZTD/pages/3244044/Creating+and+Cloning+Test+Cycles">Zephyr Test Cycle</a>

#### (3) Jenkins作業組態設定
1. 新增Jenkins作業
2. 原始碼管理：Git, 輸入本專案網址如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/git.PNG)
3. 建置：Ant (Target欄位輸入Junit junitreport), 如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/ant_2.PNG)
4. 建置後動作：發佈JUnit測試報告結果 (欄位填入junit/*.xml)，如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/junit.PNG)
5. 建置後動作：Publish test result to Zephyr for JIRA
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr_2.PNG)
6. 儲存

#### 備註：

* Appium Client Libraries Version: java-client-6.1.0
* Selenium Client Version: 3.141.59
* 若腳本中使用Customized方法, 請於叫用Ant Target欄位最前方填入build，如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/Ant%20build.PNG)

