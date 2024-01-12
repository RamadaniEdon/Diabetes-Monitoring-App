package city.org.rs;


import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplication extends ResourceConfig 
{
	public CustomApplication() 
	{
		packages("city.org.rs");

		//Register Auth Filter here
		register(AuthenticationFilter.class);
	}
}
