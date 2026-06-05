#!/usr/bin/env bash
# =============================================================================
# build_run_dsa.sh  –  Biên dịch và chạy ứng dụng DSA Digital Signature
# =============================================================================
# Cách dùng:
#   chmod +x build_run_dsa.sh
#   ./build_run_dsa.sh          # biên dịch + chạy
#   ./build_run_dsa.sh clean    # xóa file .class
#   ./build_run_dsa.sh compile  # chỉ biên dịch
# =============================================================================

set -e

SOURCES="DSASignatureParams.java DSAKeyPair.java DSASignature.java \
         DSASignatureAlgorithm.java DSAGUI.java DSAMain.java"
MAIN_CLASS="DSAMain"
JVM_OPTS="-Xmx512m -Dawt.useSystemAAFontSettings=on -Dswing.aatext=true"

# ── Màu terminal ──────────────────────────────────────────────────────────────
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'
CYAN='\033[0;36m'; BOLD='\033[1m'; NC='\033[0m'

print_banner() {
  echo -e "${CYAN}"
  echo "╔════════════════════════════════════════════════════╗"
  echo "║   🔏  DSA Digital Signature – Build & Run Script  ║"
  echo "╚════════════════════════════════════════════════════╝"
  echo -e "${NC}"
}

cmd_clean() {
  echo -e "${YELLOW}🧹 Đang xóa file .class...${NC}"
  rm -f *.class
  echo -e "${GREEN}✓ Đã xóa xong.${NC}"
}

cmd_compile() {
  echo -e "${CYAN}🔨 Biên dịch...${NC}"
  if javac -encoding UTF-8 $SOURCES; then
    echo -e "${GREEN}✅ Biên dịch thành công!${NC}"
  else
    echo -e "${RED}❌ Biên dịch thất bại. Kiểm tra lại lỗi ở trên.${NC}"
    exit 1
  fi
}

cmd_run() {
  echo -e "${CYAN}🚀 Khởi chạy ${MAIN_CLASS}...${NC}"
  java $JVM_OPTS $MAIN_CLASS
}

print_banner

case "${1:-all}" in
  clean)   cmd_clean ;;
  compile) cmd_compile ;;
  run)     cmd_run ;;
  all|*)   cmd_compile; echo; cmd_run ;;
esac
