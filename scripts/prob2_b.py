import sys
from pyspark import SparkContext

sc = SparkContext()
def transferMovieTunple(x):
	[movieID,userName,rate] = x.split(',')
	return (movieID,userName)

def transferUserTunple(x):
	[movieID,userName,rate] = x.split(',')
	return (userName,movieID)

def onlyKeyLeft(x):
	(key, oneList) = x
	return oneList

trainingFile = "hdfs:/user/training/netflix_subset/TrainingRatings.txt" # need to change
testingFile = "hdfs:/user/training/netflix_subset/TestingRatings.txt"  # need to change


totalSameNumber = 0.0
totaltry = 100000
# use movie as key
TrainData = sc.textFile(trainingFile).map(transferMovieTunple).groupByKey().mapValues(list).map(onlyKeyLeft).takeSample(True, totaltry)
TestData = sc.textFile(testingFile).map(transferMovieTunple).groupByKey().mapValues(list).map(onlyKeyLeft).takeSample(True, totaltry)

for i in range(totaltry):
	vec1 = TrainData[i]
	vec2 = TestData[i]
	totalSameNumber += len(list(set(vec1).intersection(vec2)))

print " "
print "If we use the movie as the key, the expected overlap is: " + str(totalSameNumber/totaltry)

totalSameNumber = 0.0
# use movie as key
TrainData = sc.textFile(trainingFile).map(transferUserTunple).groupByKey().mapValues(list).map(onlyKeyLeft).takeSample(True, totaltry)
TestData = sc.textFile(testingFile).map(transferUserTunple).groupByKey().mapValues(list).map(onlyKeyLeft).takeSample(True, totaltry)

for i in range(totaltry):
	vec1 = TrainData[i]
	vec2 = TestData[i]
	totalSameNumber += len(list(set(vec1).intersection(vec2)))

print " "
print "If we use the users as the key, the expected overlap is: " + str(totalSameNumber/totaltry)

