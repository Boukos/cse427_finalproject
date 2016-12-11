package stubs;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.mapred.join.TupleWritable;

public class ItemSimilarityMapper extends Mapper<LongWritable, Text, IntPairWritable, IntPairWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    /*
     * TODO implement
     */
  String line = value.toString();
 line=line.replace("(", "");
 line=line.replace(")", "");
 String[] words=line.split("\t");
 
 String[] it=words[0].split(",");
String[] it2=words[1].split(",");
//
int  item1=Integer.parseInt(it[0].trim());
int  item2=Integer.parseInt(it[1].trim());	
int rate1=Integer.parseInt(it2[0].trim());
int rate2=Integer.parseInt(it2[1].trim());
context.write(new IntPairWritable(item1,item2),new IntPairWritable(rate1,rate2));

//context.write(new IntPairWritable(words.length,words.length),new IntPairWritable(words.length,words.length));
//context.write(new IntPairWritable(item1,item1),new IntPairWritable(rate1,rate1));
  
  }
}
