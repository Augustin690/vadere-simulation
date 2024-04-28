package org.vadere.annotation.helptext;

import com.google.auto.service.AutoService;

import org.jetbrains.annotations.NotNull;
import org.vadere.annotation.ImportScanner;
import tech.tablesaw.util.StringUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import static org.vadere.util.other.Strings.removeAttribute;

@SupportedAnnotationTypes({"*"}) // run for all annotations. process must return false so annotations are not consumed
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class HelpTextAnnotationProcessor extends AbstractProcessor {
	ArrayList<Function<String, String>> pattern;
	Set<String> importedTypes;


	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		initPattern();
		processAnnotations(roundEnv);
		return false; // allow further processing
	}

	private void processAnnotations(RoundEnvironment roundEnv) {
		ImportScanner scanner = new ImportScanner();
		scanner.scan(roundEnv.getRootElements(), null);
		importedTypes = scanner.getImportedTypes();

		for (Element e: roundEnv.getRootElements()){
			processClassElements(e);
		}
	}

	private void processClassElements(Element e) {
		if ((e.getKind().isClass())  && e.asType().toString().startsWith("org.vadere.")) {
			processClass(e);
		}
	}

	private void processClass(Element e) {
		try {
			String relativePath = generateClassHelpFilepath(e);
			FileObject file = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", relativePath);

			PrintWriter w = new PrintWriter(file.openWriter());
			composeHTML(e, w);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void composeHTML(Element e, PrintWriter w) {
		composeHTMLBegin(w);
		composeHTMLHeader(e, w);
		String comment = processingEnv.getElementUtils().getDocComment(e);
		composeHTMLClassDescription(w, comment);
		composeHTMLEnd(e, w);
	}

	private void composeHTMLEnd(Element e, PrintWriter w) {
		printMemberDocString(e, w);
		w.println("</div>"); // main
		w.println("</body>");
		w.println("</html>");
		w.close();
	}

	private void composeHTMLClassDescription(PrintWriter w, String comment) {
		w.println(String.format("<div class='comment'>%s</div>", parseComment(comment)));
	}

	private void composeHTMLHeader(Element e, PrintWriter w) {
		Element superElement = getSuperElement(e);
		String title = "";
		if(isIgnorableSuperClass(superElement))
			title = composeHeaderName(e);
		else
			title = composeHeaderName(e) + " : " + composeSuperClassLink(superElement);
		w.println("<doc-header>"+title+"</doc-header>");
		w.println("<div class='main'>");
	}

	@NotNull
	private static String composeHeaderName(Element e) {
		return removeAttribute(String.format("%s", e.getSimpleName()));
	}

	private String composeSuperClassLink(Element superElement) {
		return String.format("<a href='%s' class='class_link'>%s</a>", findFullPath(String.format("%s", superElement.getSimpleName())), removeAttribute(String.format("%s", superElement.getSimpleName())));
	}

	private static Element getSuperElement(Element e) {
		return ((DeclaredType)((TypeElement)e).getSuperclass()).asElement();
	}

	private static void composeHTMLBegin(PrintWriter w) {
		w.print("<!DOCTYPE html>" +
				"<html>" +
				"<head>" +
				"</head>" +
				"<body>" +
				"{{javascript}}"
		);
	}

	private String generateClassHelpFilepath(Element e) {
		String className = e.asType().toString();
		className = className.replace("<", "_");
		className = className.replace(">", "_");
		return "helpText/" + className + ".html";
	}

	private void initPattern() {
		pattern = new ArrayList<>();
		//Local links are broken right now
		/*pattern.add( e -> {
			Pattern r = Pattern.compile("(\\{@link\\s+#)(.*?)(})");
			Matcher m = r.matcher(e);
			while (m.find()){
				e = m.replaceFirst("<span class='local_link'>$2</span>");
				m = r.matcher(e);
			}
			return e;
		});*/
		pattern.add( e -> {
			Pattern r = Pattern.compile("(\\{@link\\s+)(.*?)(})");
			Matcher m = r.matcher(e);
			while (m.find()){
				String linkId = findFullPath(m.group(2));
				String fieldType = removeAttribute(stripToBaseString(m.group(2)));
				e = m.replaceFirst(String.format("<a href='%s' class='class_link'>%s</a>", linkId,fieldType));
				m = r.matcher(e);
			}
			return e;
		});

	}

	private String findFullPath(String className){
		String n = importedTypes.stream().filter(e-> e.endsWith(className)).findFirst().orElse(className);
		return "/helpText/" + n + ".html";
	}

	private String parseComment(String multiLine){
		if(multiLine != null){
			return multiLine.lines().map(String::strip).map(this::applyMatcher).collect(Collectors.joining("\n"));
		}
		return "";
	}

	private String applyMatcher(String line){
		for(Function<String, String> p : pattern){
			line = p.apply(line);
		}
		return line;
	}
	private void printMemberDocString(Element e, PrintWriter w) {
		List<? extends Element> fields = collectHelpFields(e);
		for(Element field : fields){
			String comment = parseComment(processingEnv.getElementUtils().getDocComment(field));
			w.println(String.format("<doc-member name=\"%s\" type=\"%s\" href=\"%s\">%s</doc-member>",
					field.getSimpleName(),
					prettyPrintType(field.asType()),
					isNonVadereType(field) ? "" :findFullPath(getTypeString(field)),
					comment));
		}

	}

	@NotNull
	private static List<? extends Element> collectHelpFields(Element e) {
		return e.getEnclosedElements()
				.stream()
				.filter(o -> o.getKind().isField())
				.filter(o -> o.getAnnotation(HelpIgnore.class) == null)
				.collect(Collectors.toList());
	}

	private boolean isNonVadereType(Element field){
		return field.asType().getKind().isPrimitive() || !field.asType().toString().startsWith("org.vadere");
	}

	private String getTypeString(Element field){
		return field.asType().toString();
	}

	private String prettyPrintType(TypeMirror typeMirror){ // one depth for generic types
		var str = "";
		if(typeMirror instanceof PrimitiveType primitiveType){
			str = StringUtils.capitalize(primitiveType.toString());
		}else if (typeMirror instanceof DeclaredType declaredType) {
			str = declaredType.asElement().getSimpleName().toString(); // name of class
			if(str.startsWith("Attributes") && str.length() > "Attributes".length()){
				str = str.substring("Attributes".length());
			}
			if(!declaredType.getTypeArguments().isEmpty()){
				str += "< "; // TODO: figure out why "<" produces encoding bug
				str += declaredType.getTypeArguments().stream()
						.map(t -> prettyPrintType(t))
						.map(t -> t.startsWith("Attributes") && t.length() > "Attributes".length() ? t.substring("Attributes".length()) : t)
						.collect(Collectors.joining(","));
				str += " >";
			}
		}
		return str;
	}

	private String stripToBaseString(String str){
		return str.substring(str.lastIndexOf(".") + 1);
	}

	private boolean isIgnorableSuperClass(Element e){
		checkElementIsClass(e);
		return Arrays.stream(new String[]{
				"Object",
				"Enum",
				"Cloneable",
				"Serializable",
				"Attributes"
		}).anyMatch(s -> s.equals(e.getSimpleName().toString()));
	}

	private static void checkElementIsClass(Element e) {
		if(e.getKind() != ElementKind.CLASS)
			throw new IllegalArgumentException("Unexpected usage of this function, parameter must be an element of kind 'Class'.");
	}
}
