import sys
from pyspark import SparkContext

sc = SparkContext()
def transferMovieTunple(x):
	[movieID,userName,rate] = x.split(',')
	return (movieID,userName)

def transferUserTunple(x):
	[movieID,userName,rate] = x.split(',')
	return (userName,movieID)

trainingFile = "hdfs:/user/training/netflix_subset/TrainingRatings.txt" # need to change
testingFile = "hdfs:/user/training/netflix_subset/TestingRatings.txt"  # need to change

# use movie as key
TrainData = sc.textFile(trainingFile).map(transferMovieTunple).groupByKey().mapValues(list)
TestData = sc.textFile(testingFile).map(transferMovieTunple).groupByKey().mapValues(list)

totalSameNumber = 0
totaltry = 10000
for i in range(totaltry):
	data1 = TrainData.takeSample(False, 1)
	data2 = TestData.takeSample(False, 1)
	vec1 = data1[0][1]
	vec2 = data2[0][1]
	totalSameNumber += len(list(set(vec1).intersection(vec2)))

print "If we use the movie as the key, the expected overlap is: "+ str(float(totalSameNumber)/totaltry)


# use user as key
TrainData = sc.textFile(trainingFile).map(transferUserTunple).groupByKey().mapValues(list)
TestData = sc.textFile(testingFile).map(transferUserTunple).groupByKey().mapValues(list)

totalSameNumber = 0
totaltry = 10000
for i in range(totaltry):
	data1 = TrainData.takeSample(False, 1)
	data2 = TestData.takeSample(False, 1)
	vec1 = data1[0][1]
	vec2 = data2[0][1]
	totalSameNumber += len(list(set(vec1).intersection(vec2)))

print "If we use the user as the key, the expected overlap is: "+ str(float(totalSameNumber)/totaltry)
