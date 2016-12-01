package stubs;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;

public class UserRateMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * TODO implement
     */
	  String line = value.toString();
	  String[] words=line.split(",");
	  if(words.length==3)
	  {
	    int rate=(int)Double.parseDouble(words[2]);
	          context.write(new Text(words[1]), new IntWritable(rate));
	 
	  }
  }
}
