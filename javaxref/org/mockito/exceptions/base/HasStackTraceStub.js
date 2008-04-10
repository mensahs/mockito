defineStructure(
"HasStackTraceStub",function(factory){with(factory) { typeTable(["java.util.Arrays","cl"],["org.mockito.exceptions.base.HasStackTraceStub","cl"],["org.mockito.exceptions.base.HasStackTrace","it"],["java.lang.StackTraceElement","cl"],["java.lang.String","cl"]),methodTable([1,"HasStackTraceStub",["java.lang.StackTraceElement[]"],"co"],[1,"getStackTrace",[],"me"],[2,"getStackTrace",[],"me"],[1,"setStackTrace",["java.lang.StackTraceElement[]"],"me"],[2,"setStackTrace",["java.lang.StackTraceElement[]"],"me"],[1,"toString",[],"me"],[0,"toString",["java.lang.Object[]"],"st me"]),localVariableTable(["stackTrace","<init>(java.lang.StackTraceElement[])-stackTrace"],["stackTrace","setStackTrace(java.lang.StackTraceElement[])-stackTrace"])
return classDef(O("/*",nl," * Copyright (c) 2007 Mockito contributors",nl," * This program is made available under the terms of the MIT License.",nl," */"),nl,pa," org.mockito.exceptions.base;",nl,nl,ip," java.util.",T(0),";",nl,nl,C(1,[],$(pu,_,c,_,I("HasStackTraceStub"),_,im,_,T(2),_,B(nl,w(4),F(pi,_,T(3),"[] ",I("stackTrace"),";"),nl,w(4),nl,w(4),M(0,[],[],$(pu," HasStackTraceStub",P(V(0,$(T(3)," ... ",I("stackTrace")))),_,B(nl,w(8),t,".",G(1,"fi","stackTrace")," = ",W(0),";",nl,w(4)))),nl,nl,w(4),M(1,[2],[],$(pu,_,T(3),"[] ",I("getStackTrace"),P(),_,B(nl,w(8),r,_,G(1,"fi","stackTrace"),";",nl,w(4)))),nl,nl,w(4),M(3,[4],[],$(pu,_,v,_,I("setStackTrace"),P(V(1,$(T(3),"[] ",I("stackTrace")))),_,B(nl,w(8),t,".",G(1,"fi","stackTrace")," = ",W(1),";",nl,w(4)))),nl,w(4),nl,w(4),M(5,[],[],$(pu,_,T(4),_,I("toString"),P(),_,B(nl,w(8),r,_,T(0),".",N(6),P(G(1,"fi","stackTrace")),";",nl,w(4)))),nl))));}});