Changelog
=========

## v0.13.x [WIP]

This is a new feature set in the pre-stable life cycle, with the goal to make
the storage sub-system more strict. Previously each storage back-end was
defined as a _add-on_ which, in a production scenario, could lead to problems
with re-configuration for an already deployed system. The new goals are to
allow re-configuration for deployed systems, and have the storage back-ends
_synchronize_, allowing for migration or redundancy. Let's see how it plays
out.

* Update to latest Spring Boot Starter `2.1.2.RELEASE`.

* Update runtime system requirements to use Java 11 or higher.
  