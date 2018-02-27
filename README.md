[![Build Status](https://travis-ci.org/olle/contessa.svg?branch=master)](https://travis-ci.org/olle/contessa)

_Pssst! There's a branch for Spring Boot 2.x too. Check it out!_

Contessa
========

A very simple media content service, built on **Spring Boot**. Provides a quick
and easy way to manage the storage, upload and access to media and assets, in a
situation where a real [CDN][1] may not (yet) be a viable solution.

  [1]: https://en.wikipedia.org/wiki/Content_delivery_network


Quickstart
----------

Contessa is packaged as a fat-jar, and can be started by simply running it. The
minimal required configuration is to set the `base-dir` property, which is
required, and starting won't be possible without it.

```
  $ java -Dcontessa.base-dir=data/ -jar contessa.jar
```

You can now browse `http://localhost:8080` and you should see some application
information.

There are two main user interfaces on top of HTTP; a straight-forward web, and
a REST-ful API mapped to `http://localhost:8080/api/v1`. The REST-ful API does
not serve the actual content, but rather provides content entries as
`application/json` resources.

All content entry data is served from the web UI.

### Adding content via the web UI

Users will typically upload some named content, and capture the response, which
will be the unique content entry URI.

```
  $ curl --request POST \
         --header "content-type: application/octet-stream" \
         --data-binary "@some.gif" \
         http://localhost:8080/some.gif
  http://localhost:8080/ede542cb-f8ec1d4.gif
```

The returned URI is calculated to be unique, both with regards to the data as
well as the name.

If we request the URI we can see that Contessa will return a cache-control
header, which by default uses a `max-age` of 100 days.

```
  $ curl -I http://localhost:8080/ede542cb-f8ec1d4.gif
  HTTP/1.1 200
  ETag: "ede542cb-f8ec1d4"
  Cache-Control: max-age=8640000, public
  Content-Type: image/gif
  Content-Length: 27566
  Date: Sun, 14 Jan 2018 21:15:48 GMT
```

### Adding content via the REST interface

Submitting some content to the REST-ful interface, also requires the user to
`POST` some content with a specified name.

```
  $ curl --request POST \
         --header "content-type: application/octet-stream" \
         --data-binary "@some.gif" \
         http://localhost:8080/api/v1/some.gif
  {
    "identifier" : "ede542cb-f8ec1d4",
    "length" : 27566,
    "type" : "image/gif",
    "suffix" : "gif",
    "uri" : "http://localhost:8080/ede542cb-f8ec1d4.gif"
  }
```

The response is a small JSON object, which provides the user with information
about the content entry.

Content entry resources can also be queried on the REST-ful API, by using the
`identifier`.

```
  $ curl http://localhost:8080/api/v1/ede542cb-f8ec1d4
  {
    "identifier" : "ede542cb-f8ec1d4",
    "length" : 27566,
    "type" : "image/gif",
    "suffix" : "gif",
    "uri" : "http://localhost:8080/ede542cb-f8ec1d4.gif"
  }
```

The content entry resource property `uri` can then be used to access the actual
data.

### Adding content through the drop-box

Directly under the Contessa `base-dir`, the folder `dropbox` is created, in
order to provide support to manually add content.

By giving users access to this directory, they may drop in content files and
have Contessa repond with information about the added entry. We start Contessa
with the property `-Dcontessa.base-dir=data/`.

```
  $ ls -la data/dropbox/
  .
  ..
  $ cp some.gif data/dropbox/
  $ ls -la data/dropbox/
  .
  ..
  some.gif

(some seconds later)

  $ ls -la data/dropbox/
  .
  ..
  some.gif___ede542cb-f8ec1d4.gif
```

The dropped file will be picked up by Contessa and handled. The response is
then written back as a marker-file, which the user must take care of, and clean
out manually. Any response marker-files will of course be ignored by Contessa.

### Adding content over AMQP

If Contessa is started and the `contessa.amqp.enabled=true` property is set, it
will start and connect to a [RabbitMQ][1].

To add content entries over the message broker we need to publish a message with
the `upload` routing key, naming the file with the header `filename`. We provide
a `reply_to` for the response and add the content to the message body.

![Sending the message][1-sending]

In the overview we now see that our queue `me` has received one message.

![Received response][2-queues]

The response returned contains header fields with `identifier`, `suffix` and
the media `type`. The body payload consists of the created media entity name.

![Response content][3-received]

In this small example we can now query the media over HTTP, either using the
web or REST endpoints.

![Request content][4-request]

  [1-sending]: ./docs/img/1-send.png
  [2-queues]: ./docs/img/2-queues.png
  [3-received]: ./docs/img/3-received.png
  [4-request]: ./docs/img/4-request.png

Design &amp; Idea
-----------------

The main intent is to have a simple content service, with a strict and minimal
API surface. Users should be able to submit and retrieve, content files in some
appropriate ways.

Contessa will provide a very rich configurable out-of-the-box solution, that
offers a sensible and adaptive solution to business hosting of media content.

### Extensible structure

The design goal is to provide a separation between the two most important
sub-systems or _tiers_ - the user interface or API and the storage backend or
data repository.

#### Configurable data repository

Using a simple set of configurations, the storage of data can be controlled, and
easily set up for local filestorage, SQL or NoSQL storage.

Each storage comes with a different set of capabilities and Contessa allows for
an adaptable solution, that suites many different business goals.

#### Optional user interfaces

Accessing or uploading data can be made in several different ways. From plain
HTTP or web to REST-ful APIs, file-system polling or message bus brokers, the
feature-set is rich and very capable. This puts Contessa in a compatibility
level of its own.


Development &amp; Stack
-----------------------

The current aim and goal of Contessa is to ensure the best possible capabilities
for a meda content storage - but also, to stay within the boundaries of a very
well understood and capable technical stack.

Building on [Java][2] and [Spring Boot][3], currently mean that we can leverage
the knowledge and interest of a whole lof of developers. We believe these
developers to also be great ambassadors for Contessa, if we can provide them
with idiomatic, transparent and well thought out code.

  [2]: https://java.oracle.com
  [3]: http://projects.spring.io/spring-boot/

### Building &amp; testing

Anyone should be able to join in, and as the project evolves we will provide
more specific information. At the moment running the project should be as easy
as starting the project using the shortcut in the `Makefile`:

    > make start

There will be more documentation evolving inside the `docs/` folder.

Happy hacking!
