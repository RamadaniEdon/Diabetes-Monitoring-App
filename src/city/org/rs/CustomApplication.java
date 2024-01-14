package city.org.rs;


import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplication extends ResourceConfig 
{
	public CustomApplication() 
	{
		packages("city.org.rs");

		register(AuthenticationFilter.class);
	}
}
