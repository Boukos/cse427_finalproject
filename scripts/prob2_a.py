import sys
from pyspark import SparkContext

sc = SparkContext()
def getUserName(x):
	[movieID,userName,rate] = x.split(',')
	return userName

def getMovieID(x):
	[movieID,userName,rate] = x.split(',')
	return movieID

UserList = sc.textFile("hdfs:/user/training/netflix_subset/TrainingRatings.txt").map(getUserName).distinct()

print "total user number in training: " + str(UserList.count())


MovieList = sc.textFile("hdfs:/user/training/netflix_subset/TrainingRatings.txt").map(getMovieID).distinct()

print "total moview number in training: "+ str(MovieList.count())


UserList = sc.textFile("hdfs:/user/training/netflix_subset/TestingRatings.txt").map(getUserName).distinct()

print "total user number in testing: " + str(UserList.count())


MovieList = sc.textFile("hdfs:/user/training/netflix_subset/TestingRatings.txt").map(getMovieID).distinct()

print "total moview number in testing: "+ str(MovieList.count())



