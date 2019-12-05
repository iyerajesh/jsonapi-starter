package io.xylia.platform.jsonapi.dummy.app.data;

import org.springframework.lang.Nullable;
import com.github.jasminb.jsonapi.LongIdHandler;
import com.github.jasminb.jsonapi.annotations.Id;
import com.github.jasminb.jsonapi.annotations.Type;

@Type("users")
public class DummyUserData {

  @Id(LongIdHandler.class)
  @Nullable
  private Long id;
}
