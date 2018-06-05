enum ErrorMessages {
    
    M0("Error: Can not add vertex. Vertex already exists."),
    M1("Error: Can not remove vertex. Vertex does not exist."),
    M2("Error: Can not add edge. Vertex does not exist."),
    M3("Error: Can not add edge. Edge already exists."),
    M4("Error: Can not add edge. A vertex can not have an edge with itself."),
    M5("Error: Can not remove edge. Vertex does not exist."),
    M6("Error: Can not remove edge. Edge does not exist."),
    M7("Error: Can not remove edge. A vertex can not have an edge with itself."),
    M8("Error: Can not search neighbours. Vertex does not exist."),
    M9("Error: Can not calculate path. Vertex does not exist.");
    
    String message;
    
    ErrorMessages(String message) {
        this.message = message;
    }
}
