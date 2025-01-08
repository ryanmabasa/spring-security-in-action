For unit testing only

you find a comparison of how using annotations to define the test security environment 
differs from using a RequestPostProcessor. 
The framework interprets annotations such as @WithMockUser before it executes the test method. 
This way, the test method creates the test request and executes it in an already configured security environment. 
When using a RequestPostProcessor, the framework first calls the test method and builds the test request. 
The framework then applies the RequestPostProcessor, which alters the request or the environment in which it’s executed before sending it. In this case, the framework configures the test dependencies, 
such as the mock users and the SecurityContext, after building the test request.