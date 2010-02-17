package es.eucm.eadandroid.res.resourcehandler2;

//HELPCODE

public class GameResourceSet {
private String resourceSetName;
private Object[] resourceElements;

/**
* protected constructor that should only be visible to the class GameResources
* @param resourceSetName The resource set name which will be used to identify this resource set
* @param resourceElements The primitive array of resources
*/
protected GameResourceSet(String resourceSetName, Object[] resourceElements) {
this.resourceSetName = resourceSetName;
this.resourceElements = resourceElements;
}

/**
* Protected method freeResources. This method will only be called by the hosting GameResources object.
* The cleanup call will be done by the GameResources object and should not be done by a class outside of
* the scope of this package.
*/
protected void freeResources() {
for(int i = 0; i < resourceElements.length; i++) {
resourceElements[i] = null;
}

resourceElements = null;
}

/**
*
* @return Returns the count of resources in this resource set
*/
public int getResourceCount() {
return resourceElements.length;
}

/**
*
* @return Returns the name of this resource set
*/
public String getResourceSetName() {
return resourceSetName;
}

/**
* This method gets the resource at the specific position
* @param index The index of the resource
* @return Returns the resource
*/
public Object getResource(int index) {
if(index < 0 || index >= resourceElements.length) {
return null;
} else {
return resourceElements[index];
}
}
}
