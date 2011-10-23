/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos.io;

import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class AbstractResourceTest extends TestCase{

    public void testCreateRelativePath(){
        AbstractResourceTestImp arti =
                new AbstractResourceTestImp("/dir/", "dir/file.xml");

        TestCase.assertEquals("/dir/dir/file.xml", arti.getPath());
    }

    public void testCreateRelativePath2(){
        AbstractResourceTestImp arti =
                new AbstractResourceTestImp("//dir/otherFile.xml", "/dir//file.xml");

        TestCase.assertEquals("/dir/dir/file.xml", arti.getPath());
    }

    public void testCreateRelativePath3(){
        AbstractResourceTestImp arti =
                new AbstractResourceTestImp("//dir//otherFile.xml", "/dir/file.xml");

        TestCase.assertEquals("/dir/dir/file.xml", arti.getPath());
    }

    public void testCreateRelativePath4(){
        AbstractResourceTestImp arti =
                new AbstractResourceTestImp("/dir/otherFile.xml", "dir/file.xml");

        TestCase.assertEquals("/dir/dir/file.xml", arti.getPath());
    }

    public void testCreateRelativePath5(){
        AbstractResourceTestImp arti =
                new AbstractResourceTestImp("dir/otherFile.xml", "dir/file.xml");

        TestCase.assertEquals("dir/dir/file.xml", arti.getPath());
    }

}
