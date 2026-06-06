using System;
using System.IO;
using System.Security.Cryptography;

namespace UngDung_ChuKySo_DSA 
{
    public class DSACryptoHelper
    {
        public static void GenerateKeys(int keySize, out string publicKeyBase64, out string privateKeyBase64)
        {
            using (DSA dsa = DSA.Create(keySize))
            {
                byte[] publicKeyBytes = dsa.ExportSubjectPublicKeyInfo();
                byte[] privateKeyBytes = dsa.ExportPkcs8PrivateKey();

                publicKeyBase64 = Convert.ToBase64String(publicKeyBytes);
                privateKeyBase64 = Convert.ToBase64String(privateKeyBytes);
            }
        }

        public static byte[] SignFile(string filePath, string privateKeyBase64)
        {
            using (DSA dsa = DSA.Create())
            {
                byte[] privateKeyBytes = Convert.FromBase64String(privateKeyBase64);
                dsa.ImportPkcs8PrivateKey(privateKeyBytes, out _);

                byte[] fileData = File.ReadAllBytes(filePath);
                return dsa.SignData(fileData, HashAlgorithmName.SHA256);
            }
        }

        public static bool VerifySignature(string originalFilePath, byte[] signature, string publicKeyBase64)
        {
            using (DSA dsa = DSA.Create())
            {
                byte[] publicKeyBytes = Convert.FromBase64String(publicKeyBase64);
                dsa.ImportSubjectPublicKeyInfo(publicKeyBytes, out _);

                byte[] fileData = File.ReadAllBytes(originalFilePath);
                return dsa.VerifyData(fileData, signature, HashAlgorithmName.SHA256);
            }
        }

        public static string ByteArrayToHexString(byte[] bytes)
        {
            return BitConverter.ToString(bytes).Replace("-", " ");
        }

        public static byte[] HexStringToByteArray(string hex)
        {
            hex = hex.Replace(" ", "").Replace("\n", "").Replace("\r", "");
            byte[] bytes = new byte[hex.Length / 2];
            for (int i = 0; i < bytes.Length; i++)
            {
                bytes[i] = Convert.ToByte(hex.Substring(i * 2, 2), 16);
            }
            return bytes;
        }
    }
}