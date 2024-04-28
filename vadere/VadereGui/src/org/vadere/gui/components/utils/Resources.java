package org.vadere.gui.components.utils;


import org.vadere.util.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The Resource class is for loading, changing, adding and manipulating properties in the
 * property file [applicationName]_config.properties. The global_config.properties file
 * plays a special role. If a property is not found in the specific file
 * e. g. postvisualization_config.properties than the global_config.properties will
 * be used as second step. Note: The global_config.properties are read only!
 *
 */
public class Resources {

	private static Logger logger = Logger.getLogger(Resources.class);

	private Properties properties = null;

	private String applicationName;

	private static Map<String, Resources> instanceMap = new HashMap<>();

	public static Resources getInstance(final String applicationName) {

		if (instanceMap.get(applicationName) == null) {
			instanceMap.put(applicationName, new Resources(applicationName));
		}
		return instanceMap.get(applicationName);
	}

	private Resources(final String applicationName) {
		this.applicationName = applicationName;
	}

	public String getProperty(final String key) {
		String prop = getProperties().getProperty(key);
		if (prop == null && !this.applicationName.equals("global")) {
			prop = Resources.getInstance("global").getProperty(key);
		}

		if (prop == null && this.applicationName.equals("global")) {
			logger.warn("property " + key + " was not found.");
		}
		return prop;
	}

	public boolean getBooleanProperty(final String key) {
		return Boolean.parseBoolean(getProperty(key));
	}

	public Object setProperty(final String key, final String value) {
		return getProperties().setProperty(key, value);
	}

	public Object removeProperty(final Object key) {
		return getProperties().remove(key);
	}

	public void putProperty(final Object key, final Object value) {
		getProperties().put(key, value);
	}

	private Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			InputStream in = null;
			try {
				in = Resources.class.getResourceAsStream("/config/" + applicationName + "_config.properties");
				properties.load(in);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("topographyError while loading properties for application: " + applicationName);
			} finally {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}

		return properties;
	}

	public void save() throws IOException {
		if (properties != null) {
			BufferedOutputStream bout = null;
			try {
				URL location = Resources.class.getProtectionDomain().getCodeSource().getLocation();
				bout = new BufferedOutputStream(new FileOutputStream(location.getFile()
						+ "config/" + applicationName + "_config.properties"));
				properties.store(bout, "all the properties for the " + applicationName);
			} catch (IOException ex) {
				throw ex;
			} finally {
				if (bout != null) {
					bout.close();
				}
			}
		}
	}

	public Icon getIcon(final String name, final int iconWidth, final int iconHeight) {
		ImageIcon icon = new ImageIcon(Resources.class.getResource("/icons/" + name));
		Image img = icon.getImage().getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_AREA_AVERAGING);
		return new ImageIcon(img);
	}

	public Icon getIconSVG(final String name, final int iconWidth, final int iconHeight){
		return getIconSVG(name, iconWidth, iconHeight, new Color(0,0,0));
	}
	public Icon getIconSVG(final String name, final int iconWidth, final int iconHeight, Color newColor) {
        final Color oldColor = new Color(100, 100, 100); // override this color
		String svgString = "";
		InputStream is = Resources.class.getResourceAsStream("/icons/" + name + ".svg");
		try {
			svgString = new String(is.readAllBytes());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		String scaledSVGString = scaleSVG(iconWidth, iconHeight, svgString);
		ByteArrayInputStream svgStream = new ByteArrayInputStream(scaledSVGString.getBytes());
		BufferedImage image = null;
		try {
			image = ImageIO.read(svgStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Image bufferedImage = replaceColor(image,newColor, oldColor);
        return new ImageIcon(bufferedImage);
    }

	private static String scaleSVG(int iconWidth, int iconHeight, String svgData) {
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		Document doc;
		try {
			doc = builder.parse(new ByteArrayInputStream(svgData.getBytes()));
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Element svgElem = doc.getDocumentElement();

		svgElem.setAttribute("width", String.valueOf(iconWidth));
		svgElem.setAttribute("height", String.valueOf(iconHeight));

		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		}
		StreamResult result = new StreamResult(new StringWriter());
		try {
			transformer.transform(new DOMSource(doc), result);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}

		String scaledSVGString = result.getWriter().toString();
		return scaledSVGString;
	}

	private static Image replaceColor(BufferedImage image,Color baseColor, Color REF_COLOR) {
		float[] rgb = null;
		rgb = image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), rgb);
		if (!baseColor.equals(REF_COLOR)) {
			for (int i = 0; i < rgb.length; i+=4) {
				var r = rgb[i];
				var g = rgb[i+1];
				var b = rgb[i+2];
				if (Math.abs(r - REF_COLOR.getRed()) < 5 && Math.abs(g - REF_COLOR.getGreen())<5 && Math.abs(b - REF_COLOR.getBlue())<5) {
					rgb[i] = baseColor.getRed();
					rgb[i+1] = baseColor.getGreen();
					rgb[i+2] = baseColor.getBlue();
				}
			}
		}
		Image bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		((BufferedImage) bufferedImage).getRaster().setPixels(0, 0, image.getWidth(), image.getHeight(), rgb);
		return bufferedImage;
	}

	public Color getColor(final String name) {
		return stringToColor(getProperties().getProperty(name));
	}

	public BufferedImage getImage(final String name) {
		try {
			return ImageIO.read(Resources.class.getResource("/images/" + name));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	private static Color stringToColor(final String sColor) {
		return new Color(Integer.parseInt(sColor.substring(1, 3)), Integer.parseInt(sColor.substring(3, 5)),
				Integer.parseInt(sColor.substring(5, 7)));
	}
}
