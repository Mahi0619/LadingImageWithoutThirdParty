

Android Studio Electric Eel | 2022.1.1 Patch 2


## AGP version: 7.4.2
## Gradle version: 7.5


## To clone repository use url: git clone https://github.com/Mahi0619/LadingImageWithoutThirdParty.git


## Fragment Class:
- Annotate the class with `@AndroidEntryPoint` to enable Hilt dependency injection.
- Define ViewModel using `viewModels()` delegate provided by Jetpack's `viewModels()` Kotlin property delegate.
- Initialize ViewBinding to bind views in the fragment.
- Initialize RecyclerView with a LinearLayoutManager.
- Observe the API state using a coroutine. The `repeatOnLifecycle` function is used to automatically start and stop collecting the flow based on the fragment's lifecycle.
- Define functions to handle different API states: loading, success, failure, and empty.
- Define an `onItemClick` callback to handle item click events.

## RecyclerView Adapter Class:
- Define a RecyclerView Adapter class extending `RecyclerView.Adapter`.
- Define the ViewHolder class inside the adapter class.
- Implement `onCreateViewHolder()`, `onBindViewHolder()`, and `getItemCount()` functions.
- In the ViewHolder class, bind data to views and define a function to load images asynchronously from the network.
- Use a HashMap `localCache` to store loaded bitmaps locally to avoid reloading.

## Image Loading:
- Load images asynchronously in the background using `Thread`.
- Use `HttpURLConnection` to establish a connection and fetch the image from the URL.
- Decode the image stream into a Bitmap using `BitmapFactory.decodeStream()`.
- Set the loaded bitmap to the ImageView on the main UI thread using `imageView.post { }`.
- Cache the loaded bitmap locally using the `localCache` HashMap to avoid reloading the same image.

## Optimization:
- Implement pagination to load more items when the user scrolls to the bottom of the list.
- Limit the maximum page size to avoid excessive loading.
- Show loading indicators while fetching data from the API.
- Handle error states and empty states appropriately.
- Provide feedback to the user through Toast messages in case of errors or when the maximum page size is reached.
