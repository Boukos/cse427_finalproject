package org.apache.hadoop.finalproject;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.join.TupleWritable;

public class ColFilterJob1Reducer extends Reducer<IntWritable, IntPairWritable, IntPairWritable, IntPairWritable>{

	@Override
		public void reduce(IntWritable key, Iterable<IntPairWritable> values, Context context)
				throws IOException, InterruptedException {
			
			/*
			 * For each value in the set of values passed to us by the mapper:
			 */

			System.out.println("user " + key.get() + " " + values.toString());

			ArrayList<IntPairWritable> values_copy = new ArrayList<IntPairWritable>();
			for (IntPairWritable value_1 : values) {
				values_copy.add(new IntPairWritable(value_1.getLeft(), value_1.getRight()));
//		    	System.out.println("movie " + value_1.getLeft() + " " + value_1.getRight());
//				for (IntPairWritable value_2 : values) {
//					if (value_1.getLeft() > value_2.getLeft()) {				    	
//				    	context.write(new IntPairWritable(value_1.getLeft(), value_2.getLeft()), new IntPairWritable(value_1.getRight(), value_2.getRight()));
//					}
//				}
			}
			for (IntPairWritable value_1 : values_copy) {
		    	System.out.println("movie " + value_1.getLeft() + " " + value_1.getRight());
				for (IntPairWritable value_2 : values_copy) {
					if (value_1.getLeft() < value_2.getLeft()) {				    	
				    	context.write(new IntPairWritable(value_1.getLeft(), value_2.getLeft()), new IntPairWritable(value_1.getRight(), value_2.getRight()));
					}
				}
			}			
		}
	
}
