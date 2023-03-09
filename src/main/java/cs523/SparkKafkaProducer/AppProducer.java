package cs523.SparkKafkaProducer;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;

public class AppProducer {

	public static void main(String[] args) throws Exception {
		// Read from file
		try {
			readFile(new File(
					"/home/cloudera/workspace/SparkKafkaProducer/input/daily_14_data.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void readFile(File file) throws Exception {

		InputStream in = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String line;
		Gson gson = new Gson();
		while ((line = br.readLine()) != null) {

			WeatherData wd = gson.fromJson(line, WeatherData.class);

			System.out.println(line);

			KafkaSender.sendWeatherData(wd.getCity().getCountry(), line);
		}
		br.close();
	}

}
