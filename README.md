JavaCopyrighter (v1.0)
===============

Add the copyright and licence information on top of the __Java__ and __Xtend__ files of an Eclipse project.

How to use
==========

1. `Right click` on the project to copyright
2. Click on `Copyrighter` in the menu
3. Write a header text like `Copyright (c) 2010-2013 MyCompany`
4. Choose a licence in the combo box or create a new one by clicking on `Other`
5. Add some __contributors__ in the list
6. Click on the `OK` button

This will add the defined copyright into all the Java and Xtend files in the selected project except the _derived_ files.
This will also add or replace a file named `LICENCE.txt` at the base of the project if the License content
has been specified and is not empty (an empty licence content will __remove the file__).

Known issues
============

* If a package statement is contained in a comment before the real package statement,
the first one will be uncommented.
* Files in the default package are ignored.
