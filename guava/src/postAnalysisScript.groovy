import japicmp.model.JApiAnnotation;
import japicmp.model.JApiMethod;
import japicmp.model.JApiClass;
import japicmp.model.JApiChangeStatus;
import japicmp.model.JApiCompatibilityChange;

def isProviderType(JApiClass jApiClass) {
    def annotations = jApiClass.getAnnotations()
    for (JApiAnnotation annotation : annotations) {
        if (annotation.getFullyQualifiedName().endsWith("MyProviderType")) {
            return true
        }
    }
    return false
}

def toRemove(JApiMethod method) {
    for (JApiCompatibilityChange jApiCompatibilityChange : method.getCompatibilityChanges()) {
        if (jApiCompatibilityChange == JApiCompatibilityChange.METHOD_ADDED_TO_INTERFACE) {
            return true;
        }
    }
    return false;
}

def classes = jApiClasses.iterator()
while (classes.hasNext()) {
    def jApiClass = classes.next()
    if (jApiClass.getChangeStatus() == JApiChangeStatus.MODIFIED) {
        def isProvider = isProviderType(jApiClass)
        if (isProvider) {
            println jApiClass
            def methods = jApiClass.getMethods()
            println  "    " + methods.size() + " METHODS"
            for (JApiMethod method : methods) {
                println  "       " + method
                def isToRemove = toRemove(method)
                println "        TO REMOVE: " + isToRemove
                if (isToRemove) {
//                    jApiClass.getMethods().remove()
                }
            }
        }
    }
}

return jApiClasses
