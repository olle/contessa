Ideas
=====

In order to establish a strong design, this document tries to capture some
ideas, probe them for validity and line them up next to each other and check
how they harmonize together.

Security
--------
Due to the nature of the solution, a media content service/server, it may not
be trivial to apply more fine grained authorization structures (ACL). Also, the
broader goal of providing easy access to media content assets, will rather
benefit from a tuned-down approach: authenticated users may access any assets
they have knowledge about.

In this sense, the business model of Contessa, also implements a type of
security. Each media content asset is only accessible by it's identifier. These
identifiers are created as hard-to-guess keys, which means that unless a client
have a reference to some asset, it cannot access it.

Arbitrary listings of assets are not provided to normal clients of the media
content service. Only admins may access lists of content assets.

To this extent, there's a type of security built-in by the business rules of
the media service.

Storage
-------

Data for media content, and our primitive unit in the solution is the byte-
array. The storage abstraction should really only need to handle that type.
Other information that pertains to each media entry, may be separately kept.

The act of mapping any stored data to the media content, and other meta-data,
will at some point require the knowledge of the initial media- or mime type.

_An example of this is, if we want to provide an HTTP response back, the
content negotiation requires that we at the very minimum, provide the web
browser with the original file suffix. The majority of browsers will correctly
map such a response to a proper mime-type, displaying an image, playing a song
or showing a movie._

So this leaves us with the minimal requirements for our storage module: saving
byte arrays of media content data, and the original file type suffix, linked in
some way to the media data.

### Storage implementations

* SQL-DB - storing byte arrays and a the file suffix separately, and having
  a mapping between the two - NO PROBLEMS.

* NoSQL - sort of the same, but rather than mappings here we're more document
  oriented, meaning we need to define the structural benefit and design of the
  document type.

* Files - very few options for adding meta-data or a mapping to the original
  content name/suffix - other than... storing the byte array with the given
  suffix or using a file-name that can be mapped to some other information
  store with meta-data.

* Other... ?

### A programming model

As a developer, writing storage implementations, it would be a optimal to have
an API that harmonizes with most work that needs to be done. Below is a pseudo
code implementation, trying out some designs:

* Local file system storage

    public void store(Entry e) {

      String id = e.getId();
      String suffix = e.getSuffix();
      String name = String.format("%s.%s", id, suffix);

      File f = new File(name);
      FileOutputStream fout = ...
      fout.write(e.getData());

    }

* Database storage

    public void store(Entry e) {

        DbEntry en = new DbEntry();
        en.setId(e.getId());
        en.setSuffix(e.getSuffix());

        repo.save(en);
    }

User Interface
--------------

Access to the media assets is provided by one of several implementations. This
extends the capabilities of Contessa beyond simply being an HTTP or REST
service.

The user interface supports a small amount of actions - the ability to send or
upload an asset and receive the resulting, encoded, identifer. The idea behind
this is that the client doing the upload shall receive and store the unique
identifier for later access.

This requires, even for asynchronous channels, that we can map an upload to a
response. Only some types of transports, HTTP or open socket communication, can
provide a more stable RPC or direct response. Because of this, we want to build
a minimal session protocol (micro-session that is), so that regardless of
transport, we can maintain the upload-response semantics.

The main concern with the UI layer will be to manage the implementation, or
transport, specifics, for getting a media asset file into an entry, and
returning the entry identifier to the client.
