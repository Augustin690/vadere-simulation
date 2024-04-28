package org.vadere.gui.components.control;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebView;
import javafx.scene.Scene;
import netscape.javascript.JSObject;
import org.vadere.util.logging.Logger;


public class HelpTextView extends JFXPanel {
	private static final Logger logger = Logger.getLogger(HelpTextView.class);

	private WebView webView;

	private Stack<String> history;

	private static String javaScriptBlock = "";

	public static HelpTextView create(String className){
		HelpTextView view = new HelpTextView();
		view.loadHelpFromClass(className);
		return view;
	}

	public static boolean exists(String className){
		HelpTextView view = new HelpTextView();
		InputStream instream = view.getClass().getResourceAsStream("/helpText/" + className + ".html");
		return instream != null;
	}

	public HelpTextView() {
		this.history = new Stack<>();
		InputStream test = getClass().getResourceAsStream("/js/_internal/doc_header.js");
		if(javaScriptBlock.isEmpty())
			this.javaScriptBlock = buildJavaScriptCache();
	}

	// load all js files from the js folder
	// necessary since WebEngine does not support external js file loading
	public static String buildJavaScriptCache() {
		StringBuilder script = new StringBuilder();
		if(isJarFileContext()){
			readFromJarFile(script);
		}else{
			readFromFileSystem(script);
		}
		return "<script>" +script+ "</script>";
	}

	private static void readFromFileSystem(StringBuilder script) {
		try {
			Files.walk(new File(HelpTextView.class.getResource("/js").getFile()).toPath()).forEach(path -> {
				if(path.toString().endsWith(".js")){
					try {
						script.append(new String(Files.readAllBytes(path)));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static void readFromJarFile(StringBuilder script) {
		try {
			JarFile jarFile = new JarFile(new File(HelpTextView.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
			Enumeration<JarEntry> entries = jarFile.entries();
			entries.asIterator().forEachRemaining(entry -> {
				String name = entry.getName();
				if(name.startsWith("js") && name.endsWith(".js")){
					InputStream instream = HelpTextView.class.getResourceAsStream("/" + name);
					try {
						script.append(new String(instream.readAllBytes()));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isJarFileContext() {
		boolean isJar = HelpTextView.class.getResource("/js").getProtocol().equals("jar");
		return isJar;
	}

	public void loadHelpFromClass(String fullClassName){
		loadHelpText("/helpText/" + fullClassName + ".html");
	}

	public void loadHelpText(String helpTextId){
		if(history.empty()){
			this.history.push(helpTextId);
		}else{
			if(!history.peek().equals(helpTextId)){
				this.history.push(helpTextId);
			}
		}
		InputStream instream = getClass().getResourceAsStream(helpTextId);
		var ref = new Object() {
			String html = null;
		};
		try {
			ref.html = new String(instream.readAllBytes());
			ref.html = ref.html.replace("{{javascript}}", javaScriptBlock);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Platform.runLater(() -> {
			checkWebViewRunning();
			webView.getEngine().loadContent(ref.html);
		});
	}

	private void checkWebViewRunning() {
		if(webView == null) {
			webView = new WebView();
			setScene(new Scene(webView));
			registerLinkEvent();
			loadStyleSheet();
		}
	}

	private void registerLinkEvent() {
		webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				JSObject jsobj = (JSObject) webView.getEngine().executeScript("window");
				jsobj.setMember("java", this);
				webView.getEngine().executeScript("" +
						"document.addEventListener('click', function(e) {" +
							"let anchor = e.composedPath().find(el => el.tagName === 'A');" +
							"if(anchor) {" +
								"e.preventDefault();" +
								"java.handleLinkClick(anchor.href);" +
							"}" +
						"});"
				);

			}
		});
	}
	@SuppressWarnings("unused")
	public void handleLinkClick(String href) {
		if (href.startsWith("/helpText/")) {
			loadHelpText(href);
			//this.history.push(href);
		} else if (href.startsWith("/back")) {
			if(this.history.size() > 1) {
				this.history.pop();
				loadHelpText(this.history.peek());
			}
		}
	}

	public void loadStyleSheet(){
		Platform.runLater(() -> {
			webView.getEngine().setUserStyleSheetLocation(getClass().getResource("/docstyle/style.css").toString());
		});
	}
}
