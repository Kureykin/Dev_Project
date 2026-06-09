# SlicerApi

All URIs are relative to *http://localhost*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**slicerV1ListActiveGet**](SlicerApi.md#slicerV1ListActiveGet) | **GET** /slicer/v1/list/active |  |
| [**slicerV1ListGet**](SlicerApi.md#slicerV1ListGet) | **GET** /slicer/v1/list |  |
| [**slicerV1UrlIdDelete**](SlicerApi.md#slicerV1UrlIdDelete) | **DELETE** /slicer/v1/url/{id} |  |
| [**slicerV1UrlIdGet**](SlicerApi.md#slicerV1UrlIdGet) | **GET** /slicer/v1/url/{id} |  |
| [**slicerV1UrlPost**](SlicerApi.md#slicerV1UrlPost) | **POST** /slicer/v1/url |  |
| [**slicerV1UrlPut**](SlicerApi.md#slicerV1UrlPut) | **PUT** /slicer/v1/url |  |


<a id="slicerV1ListActiveGet"></a>
# **slicerV1ListActiveGet**
> List&lt;UrlData&gt; slicerV1ListActiveGet()



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    try {
      List<UrlData> result = apiInstance.slicerV1ListActiveGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1ListActiveGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UrlData&gt;**](UrlData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **0** | Something went wrong |  -  |

<a id="slicerV1ListGet"></a>
# **slicerV1ListGet**
> List&lt;UrlData&gt; slicerV1ListGet()



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    try {
      List<UrlData> result = apiInstance.slicerV1ListGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1ListGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;UrlData&gt;**](UrlData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **0** | Something went wrong |  -  |

<a id="slicerV1UrlIdDelete"></a>
# **slicerV1UrlIdDelete**
> UrlData slicerV1UrlIdDelete(id)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      UrlData result = apiInstance.slicerV1UrlIdDelete(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1UrlIdDelete");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

[**UrlData**](UrlData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **4XX** | Link not found |  -  |
| **0** | Something went wrong |  -  |

<a id="slicerV1UrlIdGet"></a>
# **slicerV1UrlIdGet**
> slicerV1UrlIdGet(id)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      apiInstance.slicerV1UrlIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1UrlIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **4XX** | Link not found |  -  |
| **0** | Something want wrong |  -  |

<a id="slicerV1UrlPost"></a>
# **slicerV1UrlPost**
> String slicerV1UrlPost()



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    try {
      String result = apiInstance.slicerV1UrlPost();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1UrlPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **4XX** | User not found |  -  |
| **0** | Something went wrong |  -  |

<a id="slicerV1UrlPut"></a>
# **slicerV1UrlPut**
> UrlData slicerV1UrlPut(editRequest)



### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SlicerApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost");

    SlicerApi apiInstance = new SlicerApi(defaultClient);
    EditRequest editRequest = new EditRequest(); // EditRequest | 
    try {
      UrlData result = apiInstance.slicerV1UrlPut(editRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling SlicerApi#slicerV1UrlPut");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **editRequest** | [**EditRequest**](EditRequest.md)|  | [optional] |

### Return type

[**UrlData**](UrlData.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **2XX** | OK |  -  |
| **0** | Something went wrong |  -  |

