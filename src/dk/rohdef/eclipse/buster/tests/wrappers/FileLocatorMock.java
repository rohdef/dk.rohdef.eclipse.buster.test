package dk.rohdef.eclipse.buster.tests.wrappers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Bundle;

import dk.rohdef.eclipse.buster.wrappers.IFileLocatorWrapper;

public class FileLocatorMock implements IFileLocatorWrapper {
	@Override
	public URL find(Bundle bundle, IPath path, Map<String, String> override) {
		try {
			return new URL("file://" + path.toOSString());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not parse mocked path to url",
					e);
		}
	}
}
