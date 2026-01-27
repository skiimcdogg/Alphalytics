# env variables, centered configuration
from pydantic_settings import BaseSettings
from functools import lru_cache

class Settings(BaseSettings):
    """
    Application settings via environment variables
    """
    app_name: str = "Alphalytics"
    environment: str = "development"
    debug: bool = True

    database_url: str = "sqlite:///./trading.db"

    api_prefix: str = "/api"

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = False

@lru_cache()
def get_settings() -> Settings:
    """
    Return application settings as singleton
    """
    return Settings()
