# CSE427 Final Project
## Overview
The project is configured by [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html). All tools needed are contained inside the course virtual machine.

## Project setup (in virtual machine)
In the following instructions, `${project_root}` denotes the `code/colfilterjava` folder. It should contain a `pom.xml` file.
### Start package manager
In terminal, execute:
```bash
cd /usr/local/archiva/apache-archiva-1.3.9/bin
sudo ./archiva console
```
Keep it running.
### Initialize Maven
Under `${project_root}`, execute:
```bash
mvn clean
```
Maven will then download all dependencies from the remote repository. It can take some time. After this step, you can close archiva.
### Run a test
The default project implements a WordCount. First compile and package the project:
```bash
mvn package
```
The jar is located under `${project_root}/target`. Run the following test to validate the setting:
```bash
hadoop jar colfilterjava-1.0-SNAPSHOT.jar org.apache.hadoop.finalproject.ColFilterDriver -fs file:/// -jt=local /home/training/training_materials/developer/data/shakespeare result_shakespeare
```
### Open the project with Eclipse
You are ready to edit code! Open Eclipse, click 'File->Import'. In the new dialog, choose 'Maven'-> 'Existing Maven Projects'. In the next dialog, click the 'Browse' button on the top right, select `${project_root}`. Click 'Finish'.

Make sure that you are editing the code in place. You can check this by right click any files in the project, click 'Properties' and check the file path. If you do not import the project properly, Eclipse might copy the project into `~/workspace`. It is not a problem in terms of coding and debugging, but git will lost track of your editing.

To run the program locally inside Eclipse, you don't have to add external jars (as stated in [Lab 3](http://www.cse.wustl.edu/~m.neumann/fl2016/cse427/protected/Lab3b_RunJobLocally.pdf)). All needed jars are taken care by Maven. However, I haven't figured out way to specify `log4j.properties` in Eclipse. When you run the program locally inside Eclipse, there will be three warnings in output window, complaining that it can not find appenders. Just ignore the warnings. If you really need the logging messages, run the program from command line.

## Cloud execution
The following commands assume you are under `${project_root}` directory (e.g. `$git_root/code/colfilterjava/`).
### Step 1 (only when you modified the code): Build the local package
```bash
mvn package
```
### Step 2: Upload the package to the cluster
```bash
scp target/colfilterjava-1.0-SNAPSHOT.jar cse427s@cse427-ssh.azurehdinsight.net:/home/cse427s/
```
Notice: there is "cse427" without 's' in the cluster name, but "cse427s" with 's' in the user name.
### Step 3: Run the job(s) on the cluster
First ssh into the cluster:
```bash
ssh cse427s@cse427-ssh.azurehdinsight.net
```
The password will be in the WeChat group.
The data should be already in the HDFS under the root directory(/). To verify this, type
```bash
hadoop fs -ls /
```
You should find netflix_subset folder and (optionally) the output of intermediate jobs (result_job1, result_item, result_topk).
To run all jobs, execute (WARNING: this will clear all the result)
```bash
./run_all.sh
```
To run only top-k list job, execute (WARNING: this will clear result_topk)
```bash
./run_topk.sh
```
### Step 4: Get the result from HDFS and copy to local.
## Report issues
If you find any issues during setting up, report it through [HERE](https://github.com/yanhangpublic/cse427_finalproject/issues/new).
