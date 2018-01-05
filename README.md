# Welcome

#### 使用前須知：
* 測試腳本撰寫及Appium相關說明請參考<a href="https://github.com/Gilleschen/Appium_Auto_Testing_Android">Appium_Auto_Testing_Android</a>
* 本專案無法多台裝置序列測試

#### (1) Jenkins設定
1. 安裝 <a href="https://jenkins-ci.org/">Jenkins</a>
2. 進入 Global Tool Configuration (設定JDK, Git, Ant, 如下圖)
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/Global%20Tool%20Configuration.png)
3. 於Jenkins安裝 <a href="https://wiki.jenkins.io/display/JENKINS/Zephyr+For+Jira+Test+Management+Plugin">Zephyr for JIRA Test Management plugin</a> (進入設定系統-> Zephyr For JIRA - Test Management Configuration，如下圖)
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr%20_configurate.PNG)

#### (2) Zephyr設定
1. 建立空白 <a href="https://zephyrdocs.atlassian.net/wiki/spaces/ZTD/pages/3244044/Creating+and+Cloning+Test+Cycles">Zephyr Test Cycle</a>

#### (3) Jenkins作業組態設定
1. 新增Jenkins作業
2. 原始碼管理：Git，如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/git.PNG)
3. 建置：Ant (Target欄位輸入Junit junitreport)，如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/ant_2.PNG)
4. 建置後動作：發佈JUnit測試報告結果 (欄位填入junit/*.xml)，如下圖
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/junit.PNG)
5. 建置後動作：Publish test result to Zephyr for JIRA
![image](https://github.com/Gilleschen/Zephyr-Jenkins/blob/master/picture/zephyr_2.PNG)
6. 儲存
