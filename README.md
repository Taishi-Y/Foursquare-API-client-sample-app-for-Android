## Foursquare API client sample app for Android

![main screen](https://github.com/Taishi-Y/Foursquare-API-client-sample-app-for-Android/blob/master/images/screenshot.png?raw=true)


### Overview

This project is getting venue datas from Foursquare API.( https://developer.foursquare.com/overview/ )

You can just clone this sample repository and know how it works very easily.
Personally I think Foursquare API is good for learning how to get and parse JSON data.


### Set up

Get your "Client_ID" and "Client_Secret" from this link:
https://ja.foursquare.com/developers/apps

Set your  "Client_ID" and "Client_Secret":

```java
public class Configuration {
	String Client_ID = "YOUR CLIENT ID";
	String Client_Secret = "YOUR CLIENT SECRET";
}
```

Make sure to require Internet permissions in your AndroidManifest.xml file:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />
</manifest>
```

Add the following to your app/build.gradle file:
```java
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'

provided 'org.glassfish:javax.annotation:10.0-b28'
```



### 1. Creating the Retrofit instance
```java
Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.foursquare.com/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
```
### 2. Define the Endpoints
For instance, the interface defines each endpoint in the following way:
```java
public interface FourSquareService {

	@GET("venues/explore/")
	Call<Explore> requestExplore(
			@Query("client_id") String client_id,
			@Query("client_secret") String client_secret,
			@Query("v") String v,
			@Query("ll") String ll,
			@Query("query") String query);


	Retrofit retrofit = new Retrofit.Builder()
			.baseUrl("https://api.foursquare.com/v2/")
			.addConverterFactory(GsonConverterFactory.create())
			.build();
}
```

### 3. Make model classes
You can get venues data json response from this link.
(https://developer.foursquare.com/docs/venues/explore)


Make model classes by using jsonschema2pojo. (http://www.jsonschema2pojo.org/)
You can learn how to make pojo automatically from this website.
After you make model classes you should download as a zip file and put all model classes into your project.(E.g. YOURPROJECT>app>src>main>java>com.exmaple.yourproject>model)
https://guides.codepath.com/android/Consuming-APIs-with-Retrofit#create-java-classes-for-resources


### 4. Make layout files
activity_main.xlm :
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.taishi.foursquareapiforandroid.MainActivity">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Latitude and Longitude"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="40.7,-74"
            android:ems="10"
            android:id="@+id/et_geolocation"/>
    </LinearLayout>

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="query"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="cafe"
            android:ems="10"
            android:id="@+id/et_query"/>
    </LinearLayout>

    <Button
        android:text="Search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn_search"/>

    <ListView
        android:id="@+id/listivew"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


</LinearLayout>

```

item_list.xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    android:orientation="vertical"
    tools:context="com.taishi.foursquareapiforandroid.MainActivity">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Latitude and Longitude"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="40.7,-74"
            android:ems="10"
            android:id="@+id/et_geolocation"/>
    </LinearLayout>

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="query"/>

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textPersonName"
            android:text="cafe"
            android:ems="10"
            android:id="@+id/et_query"/>
    </LinearLayout>

    <Button
        android:text="Search"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn_search"/>

    <ListView
        android:id="@+id/listivew"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>


</LinearLayout>
```



### 5. Make MainActivity

MainActivity.java:
```java
public class MainActivity extends AppCompatActivity {

	EditText etGeolocation, etQuery;
	Button btnSearch;
	ListView listView;
	String Client_ID = "YOUR_CLIENT_ID";
	String Client_Secret = "YOUR_CLIENT_SECRET";
	String apiVersion = "20161010";
	String geoLocation = "40.7,-74";
	String query = "cafe";


	List<Item_> item_list = new ArrayList<Item_>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewByIds();
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ExploreAsyncTask exploreAsyncTask = new ExploreAsyncTask();
				exploreAsyncTask.execute();
			}
		});
	}
	void findViewByIds(){
		etGeolocation = (EditText) findViewById(R.id.et_geolocation);
		etQuery = (EditText) findViewById(R.id.et_query);
		btnSearch = (Button) findViewById(R.id.btn_search);
		listView = (ListView)findViewById(R.id.listivew);
	}

	public class ExploreAsyncTask extends AsyncTask<Void,Void,List<Item_>> {

		public ExploreAsyncTask() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected List<Item_> doInBackground(Void... voids) {
			FourSquareService fourSquareService = FourSquareService.retrofit.create(FourSquareService.class);
			final Call<Explore> call = fourSquareService.requestExplore(Client_ID, Client_Secret, apiVersion, geoLocation, query);

			try {
				Explore explore =  call.execute().body();
				item_list = explore.getResponse().getGroups().get(0).getItems();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Item_> item_s) {
			super.onPostExecute(item_s);
			ExploreListAdapter exploreListAdapter = new ExploreListAdapter(getApplicationContext(), R.layout.item_list, item_list);
			listView.setAdapter(exploreListAdapter);
		}
	}
}

```
### 6. Make adapter class for ListView

```java
public class ExploreListAdapter extends ArrayAdapter<Item_> {
	private LayoutInflater layoutInflater_;

	public ExploreListAdapter(Context context, int layout, List<Item_> objects) {
		super(context, layout, objects);
		layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 特定の行(position)のデータを得る
		Item_ item_ = getItem(position);

		String name = item_.getVenue().getName();
		double point = item_.getVenue().getRating();
		String genre = item_.getVenue().getCategories().get(0).getName();
		int distance = item_.getVenue().getLocation().getDistance();
		String comment = item_.getTips().get(0).getText();



		// convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		if (null == convertView) {
			convertView = layoutInflater_.inflate(R.layout.item_list, null);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.tv_item_name);
		TextView tvPoint = (TextView) convertView.findViewById(R.id.tv_list_point);
		TextView tvGenre = (TextView) convertView.findViewById(R.id.tv_item_genre);
		TextView tvDistance = (TextView) convertView.findViewById(R.id.tv_item_distance);
		TextView tvComment = (TextView) convertView.findViewById(R.id.tv_item_comment);



		tvName.setText(name);
		tvPoint.setText(String.valueOf(point));
		tvGenre.setText(genre);
		tvDistance.setText(String.valueOf(distance	));
		tvComment.setText(comment);

		return convertView;
	}
}
```

Thanks!
Feel free to ask me! :)
https://twitter.com/taishi0917
