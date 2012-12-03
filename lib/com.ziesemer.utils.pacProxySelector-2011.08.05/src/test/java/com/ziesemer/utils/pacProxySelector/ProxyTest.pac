function FindProxyForURL(url, host){
	if(dnsDomainIs(host, "multiple.echo.test.")){
		return "PROXY " + host + ":80; "
			+ "SOCKS " + host + ":81; "
			+ "DIRECT";
	}else if(dnsDomainIs(host, ".echo.test.")){
		if(url.indexOf("socket://") == 0){
			return "SOCKS " + host + ":80";
		}
		return "PROXY " + host + ":80";
	}
	return "DIRECT";
}

// Tests
(function(){
	
	var assert = function(test, msg){
		if(!test){
			throw "assertion error: " + msg;
		}
	};
	
	alert("Testing functions available...");
	["isPlainHostName", "dnsDomainIs", "localHostOrDomainIs", "isResolvable", "isInNet",
		"dnsResolve", "myIpAddress", "dnsDomainLevels",
		"shExpMatch",
		"weekdayRange", "dateRange", "timeRange"]
	.forEach(function(f){
		assert(typeof(this[f]) == "function", f + " is not a function.");
	});
	
	// example.com, example.net, and example.org are guaranteed by RFC 2606.
	alert("Testing isPlainHostName...");
	assert(isPlainHostName("example"), "isPlainHostName true");
	assert(!isPlainHostName("example.com"), "isPlainHostName false");
	
	alert("Testing dnsDomainIs...");
	assert(dnsDomainIs("www.example.com", "example.com"), "dnsDomainIs true");
	// Following currently returns false, but not certain what the correct answer should be.
	// assert(dnsDomainIs("www.example.com", "example.com."), "dnsDomainIs true #2");
	assert(!dnsDomainIs("www", "example.com"), "dnsDomainIs false");
	
	alert("Testing localHostOrDomainIs...");
	assert(localHostOrDomainIs("www.example.com", "www.example.com"), "localHostOrDomainIs true #1");
	assert(localHostOrDomainIs("www", "www.example.com"), "localHostOrDomainIs true #2");
	assert(!localHostOrDomainIs("example.com", "www.example.com"), "localHostOrDomainIs false");
	
	// RFC 2606 sections 2-3
	alert("Testing isResolvable...");
	assert(isResolvable("example.com"), "isResolvable true");
	assert(!isResolvable("example.invalid"), "isResolvable true");
	
	alert("Testing isInInet...");
	assert(isInNet("192.168.0.1", "192.168.0.1", "255.255.255.255"), "isInNet true #1");
	assert(isInNet("192.168.1.1", "192.168.0.0", "255.255.0.0"), "isInNet true #2");
	assert(!isInNet("192.168.1.1", "192.168.0.1", "255.255.255.255"), "isInNet false");
	
	alert("Testing dnsResolve...");
	assert(dnsResolve("localhost") == "127.0.0.1", "dnsResolve");
	
	alert("Testing myIpAddress...");
	assert(/(\d+\.){3}\d+/.test(myIpAddress()), "myIpAddress");
	
	alert("Testing dnsDomainLevels...");
	assert(dnsDomainLevels("www") == 0, "dnsDomainLevels #1");
	assert(dnsDomainLevels("www.example.com") == 2, "dnsDomainLevels #2");
	
	// Currently will fail, but not certain what the correct answer should be.
	// (Currently compatible with the implementation in Firefox 3.x).
	// assert(dnsDomainLevels("www.example.com.") == 2, "dnsDomainLevels #3");
	
	alert("Testing shExpMatch...");
	assert(shExpMatch("http://www.example.com", "*example*"), "shExpMatch true #1");
	assert(shExpMatch("http://www.example.com", "*.com"), "shExpMatch true #2");
	assert(!shExpMatch("http://www.example.com", "*.net"), "shExpMatch false");

	// Not really possible to further test without having control of the date,
	//   or duplicating the logic.
	alert("Testing weekdayRange...");
	assert(weekdayRange("SUN", "SAT"), "weekdayRange");
	alert("Testing dateRange...");
	assert(dateRange(1, 31), "dateRange days");
	assert(dateRange("JAN", "DEC"), "dateRange months");
	alert("Testing timeRange...");
	assert(timeRange(0, 23), "timeRange hours");
	assert(!timeRange(24), "timeRange hour 24");
	assert(timeRange(0, 0, 23, 59), "timeRange hm");
	assert(timeRange(0, 0, 0, 23, 59, 59), "timeRange hms");
})();