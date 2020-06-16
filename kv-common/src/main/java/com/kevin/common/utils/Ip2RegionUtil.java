//package com.kevin.common.utils;
//
//
//import cn.hutool.db.ds.pooled.DbConfig;
//import com.kevin.common.utils.model.IpInfo;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.util.ResourceUtils;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
///**
// * Ip2Region 本地获取ip地址工具
// *
// * @author novel
// * @date 2020/3/24
// */
//public class Ip2RegionUtil {
//    private static final String PATH = "/ip2region.db";
//    private static byte[] data;
//
//    static {
//        try {
//            InputStream inputStream = new ClassPathResource(PATH).getInputStream();
//            data = FileCopyUtils.copyToByteArray(inputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                String filePath = ResourceUtils.getURL("classpath:").getPath() + PATH;
//                filePath = filePath.substring(1);
//                Path path = Paths.get(filePath);
//                data = Files.readAllBytes(path);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 获取ip地址
//     *
//     * @param ip ipv4
//     * @return 地址信息
//     */
//    public static IpInfo find(String ip) {
//        try {
//            DbConfig config = new DbConfig();
////            String dbFile = Ip2RegionUtil.class.getResource(PATH).getPath();
//            DbSearcher searcher = new DbSearcher(config, data);
//
//            //采用Btree搜索
//            DataBlock block = searcher.memorySearch(ip);
//
//            //打印位置信息（格式：国家|大区|省份|城市|运营商）
//            String[] split = block.getRegion().split("\\|");
//            IpInfo info = new IpInfo();
//
//
//            info.setCountry(split[0]);
//            info.setArea(split[1]);
//            info.setProvince(split[2]);
//            info.setCity(split[3]);
//            info.setIsp(split[4]);
//
//            return info;
//        } catch (DbMakerConfigException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
