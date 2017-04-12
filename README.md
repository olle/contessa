Contessa
========

A simple web media content service, built on **Spring Boot**. Provides a quick
and easy way to control serving of media and assets, in a situation where a
real [CDN][1] is not an option.

  [1]: https://en.wikipedia.org/wiki/Content_delivery_network

Design &amp; Idea
-----------------

The main intent is to have a simple content service, with a strict and minimal
API surface. Users should be able to POST content files, and receive a unique
URI for that content.

Contessa will provide hash-based caching, and any unique content will be
delivered with appropriate headers.

Plug-In Backends
----------------

Our aim is to ensure an easy and _pluggable_ moduler structure in the project.
This is _easy_ as we're using [Spring Boot][2]. We want to provide several
options out-of-the-box, with the added capability of users extending on their
own.

  [2]: http://projects.spring.io/spring-boot/

Happy hacking!
