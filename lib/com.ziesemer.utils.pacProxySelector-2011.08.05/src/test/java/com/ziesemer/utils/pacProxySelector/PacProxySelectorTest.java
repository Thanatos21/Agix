package com.ziesemer.utils.pacProxySelector;

import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.Proxy.Type;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.ziesemer.utils.junit.BaseTest;

/**
 * @author Mark A. Ziesemer
 * <a href="http://www.ziesemer.com.">&lt;www.ziesemer.com&gt;</a>
 */
public class PacProxySelectorTest extends BaseTest{
	@Test
	public void testPac() throws Exception{
		InputStreamReader isr = new InputStreamReader(
			getClass().getResourceAsStream("ProxyTest.pac"));
		
		// Tests within PAC file will be executed when the script is read,
		//   and will throw exceptions for failures.
		ProxySelector ps = new PacProxySelector(isr);
		
		// Tests below verify the mappings from the PAC to the ProxySelector.
		Assert.assertEquals(
			Arrays.asList(Proxy.NO_PROXY),
			ps.select(new URI("http://example.com")));
		
		Assert.assertEquals(
			Arrays.asList(new Proxy(Type.HTTP, new InetSocketAddress("example.echo.test.", 80))),
			ps.select(new URI("http://example.echo.test.")));
		
		Assert.assertEquals(
			Arrays.asList(new Proxy(Type.SOCKS, new InetSocketAddress("example.echo.test.", 80))),
			ps.select(new URI("socket://example.echo.test.")));
		
		Assert.assertEquals(
			Arrays.asList(
				new Proxy(Type.HTTP, new InetSocketAddress("multiple.echo.test.", 80)),
				new Proxy(Type.SOCKS, new InetSocketAddress("multiple.echo.test.", 81)),
				Proxy.NO_PROXY),
			ps.select(new URI("http://multiple.echo.test.")));
	}
	
	@Test
	public void testMain() throws Exception{
		System.setProperty("proxy.autoConfig",
			getClass().getResource("SimpleSample.pac").toString());
		String[] args = {MainTest.class.getName(), "arg1", "arg2", "arg3"};
		PacProxySelector.main(args);
		Assert.assertArrayEquals(new String[]{"arg1", "arg2", "arg3"}, MainTest.args);
		Assert.assertTrue(ProxySelector.getDefault() instanceof PacProxySelector);
	}
}
