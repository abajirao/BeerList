package com.example.beerlist;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BeerList extends ListActivity {

	private static final String TAG = BeerList.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Open the JSON file from assets and parse it.
		AssetManager assetManager = getAssets();
		try {
			InputStream inputStream = assetManager.open("beers.json");
			ArrayList<Beer> beerList = parseBeers(inputStream);
			BeerAdapter beerAdapter = new BeerAdapter(this, 0, 0, beerList);
			setListAdapter(beerAdapter);

		} catch (IOException e) {
			e.printStackTrace();
		}

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
	}

	
	//Parse the json and create a list of Beer objects.
	private ArrayList<Beer> parseBeers(InputStream inputStream)
			throws UnsupportedEncodingException {
		JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
		ArrayList<Beer> beerList = null;
		try {
			beerList = new ArrayList<Beer>();
			beerList = readBeersArray(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return beerList;

	}

	private ArrayList<Beer> readBeersArray(JsonReader reader)
			throws IOException {
		ArrayList<Beer> beerList = new ArrayList();
		reader.beginArray();
		while (reader.hasNext()) {
			beerList.add(readBeer(reader));
		}
		reader.endArray();
		return beerList;

	}

	private Beer readBeer(JsonReader reader) throws IOException {
		Beer beer = new Beer();
		reader.beginObject();

		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("image")) {
				beer.setImagePath(reader.nextString());
			} else if (name.equals("type")) {
				beer.setBeerType(reader.nextString());
			} else if (name.equals("name")) {
				beer.setBeerName(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return beer;
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beer_list, menu);
		return true;
	}
	
	
	//Beer Adapter is the adapter we use to populate the data and bind it to the ListView.
	public static class BeerAdapter extends ArrayAdapter<Beer> {

		Context context;
		ArrayList<Beer> beerList;

		public BeerAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<Beer> beerList) {
			super(context, resource, textViewResourceId, beerList);
			this.context = context;
			this.beerList = beerList;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Beer beer = beerList.get(position);

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.row_item, parent, false);

			ImageView beerIcon = (ImageView) row.findViewById(R.id.beer_icon);
			TextView textView = (TextView) row.findViewById(R.id.textView1);

			textView.setText(beer.getBeerName());
			String imagePath = beer.getImagePath();
			imagePath = imagePath.substring(0, imagePath.lastIndexOf("."));
			beerIcon.setImageResource(context.getResources().getIdentifier(imagePath,
					"drawable", context.getPackageName()));
			return row;
		}

		@Override
		public int getCount() {
			return beerList.size();
		}

	}
}
