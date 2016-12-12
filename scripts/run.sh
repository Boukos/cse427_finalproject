echo "Cleaning previous results"
hadoop fs -rm -r /result_job1 /result_item /result_topk
echo "Running Job1"
yarn jar colfilterjava-1.0-SNAPSHOT.jar org.apache.hadoop.finalproject.ColFilterJob1Driver /netflix_subset/TrainingRatings.txt /result_job1
echo "Running ItemSimilarity"
yarn jar colfilterjava-1.0-SNAPSHOT.jar org.apache.hadoop.finalproject.ItemSimilarityDriver /result_job1 /result_item
echo "Runing TopKDriver"
yarn jar colfilterjava-1.0-SNAPSHOT.jar org.apache.hadoop.finalproject.TopKDriver /result_item /result_topk
echo "All done"
