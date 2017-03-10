Contessa
========

A simple web media content service, built on **Spring Boot**. Provides a quick
and easy way to control serving of media and assets, in a situation where a
real [CDN][1] is not an option.

  [1]: https://en.wikipedia.org/wiki/Content_delivery_network

Design &amp; Idea
-----------------

The main intent is to have a simple content service, with a strict and minimal
API surface. Users should be able to POST content files. Receive a unique URI
for that content.

This way we can ensure that caching is always configured to the highest most
benefit of clients.

Happy hacking!
