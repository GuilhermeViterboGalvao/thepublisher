import groovy.text.SimpleTemplateEngine as STE
def data = new XmlSlurper().parse(xmlFile)
def engine = new STE()
def template = engine.createTemplate(templateFile)
template.make([info: data]).toString()
