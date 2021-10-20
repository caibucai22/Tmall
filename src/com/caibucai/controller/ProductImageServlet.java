package com.caibucai.controller;

import com.caibucai.bean.Product;
import com.caibucai.bean.ProductImage;
import com.caibucai.dao.ProductImageDao;
import com.caibucai.utils.ImageUtil;
import com.caibucai.utils.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Csy
 * @Classname ProductImageServlet
 * @date 2021-10-19 11:34
 * @Description TODO
 */
public class ProductImageServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        InputStream is = null;
        // �ύ�ϴ��ļ�ʱ����������
        Map<String,String> params = new HashMap<>();

        // �����ϴ�
        is = parseUpload(request,response,params);
        // �����ϴ��Ĳ�������productImage����
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product p = productDao.get(pid);

        ProductImage pi = new ProductImage();
        pi.setType(type);
        pi.setProduct(p);
        productImageDao.add(pi);

        //�����ļ�
        String fileName = pi.getId()+".jpg";
        String imageFolder;
        String imageFolder_samll = null;
        String imageFolder_middle = null;

        if(ProductImageDao.type_single.equals(pi.getType())){
            imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_samll = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productMiddle");

        }else{
            imageFolder= request.getSession().getServletContext().getRealPath("img/productDetail");
        }
        File f = new File(imageFolder,fileName);
        f.getParentFile().mkdirs();

        // �����ļ�
        try{
            if(null != is && 0!= is.available()){
                try(FileOutputStream fos = new FileOutputStream(f)){
                    byte[] bytes = new byte[1024*1024];
                    int length = 0;
                    while(-1 != (length = is.read(bytes))){
                        fos.write(bytes,0,length);
                    }
                    fos.flush();

                    // ͨ�����´��룬���ļ�����Ϊjpg��ʽ
                    BufferedImage img = ImageUtil.change2jpg(f);
                    ImageIO.write(img,"jpg",f);

                    if(ProductImageDao.type_single.equals(pi.getType())){
                        File f_small = new File(imageFolder_samll,fileName);
                        File f_middle = new File(imageFolder_middle,fileName);

                        ImageUtil.resizeImage(f,56,56,f_small);
                        ImageUtil.resizeImage(f,217,190,f_middle);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "@admin_productImage_list?pid="+p.getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }
}
